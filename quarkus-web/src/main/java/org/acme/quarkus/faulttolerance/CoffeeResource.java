package org.acme.quarkus.faulttolerance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/coffee")
@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
public class CoffeeResource{
  private static final Logger log = LoggerFactory.getLogger(CoffeeResource.class.getName());

  @Inject
  private CoffeeRepositoryService service;
  private AtomicLong counter = new AtomicLong(0);

  @GET
  @Retry(maxRetries = 3)
  public List<Coffee> coffees(){
    final Long invocationNumber = counter.getAndIncrement();
    maybeFail(String.format("CoffeeResource#coffees() 触发 #%d  失败",
     invocationNumber));
    log.info("CoffeeResource#coffees() 触发 {}  失败", invocationNumber);
    return service.getAllCoffees();
  }

  @GET
  @Path("{id}")
  @Timeout(250)
  @Fallback(fallbackMethod = "fallbackRecommendations")
  public List<Coffee> recommendations(@PathParam int id){
    long started = System.currentTimeMillis();
    final long invocationNumber = counter.getAndIncrement();
    try{
      randomDelay();
      log.info("CoffeeResource#recommendations() invocation {} returning successfully", 
        invocationNumber);
      return service.getRecommendations(id);
    }catch(InterruptedException e){
      log.error("CoffeeResource#recommendations() invocation {} returning successfully", 
        invocationNumber);
        return null;
    }
  }

  @GET
  @Path("{id}/availability")
  public Response availability(@PathParam int id){
    final Long invocationNumber = counter.getAndIncrement();
    Coffee coffee = service.getCoffeeById(id);
    if(coffee == null){
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    try {
      Integer availability = service.getAvailability(coffee);
      log.info("CoffeeResource#availability() 触发 {} 成功!", invocationNumber);
      return Response.ok(availability).type(MediaType.APPLICATION_JSON).build();
    } catch (Exception e) {
      String message = String.format("CoffeeResource#availability() 触发 #%d 失败!", invocationNumber);
      log.error(message);
	    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(message)
        .type(MediaType.APPLICATION_JSON)
        .build();
    }
    
    
  }
  private List<Coffee> fallbackRecommendations(int id){
    log.info("Fallback method trigger!");
    return Collections.singletonList(service.getCoffeeById(1));

  }
  private void randomDelay() throws InterruptedException {
    Thread.sleep(new Random().nextInt(500));
  }

private void maybeFail(String failureMessage) {
    if ( new Random().nextBoolean()){
      log.error(failureMessage);
      throw new RuntimeException("Rest Resource failure");
    }
  }

  
}

@ApplicationScoped
class CoffeeRepositoryService {
  private static final Logger log = LoggerFactory.getLogger(CoffeeRepositoryService.class.getName());
  private Map<Integer, Coffee> coffeeList = new HashMap<>();
  private AtomicLong counter = new AtomicLong(0);
  /**
   * 
   */
  public CoffeeRepositoryService() {
    coffeeList.put(1, new Coffee(1, "Fernandez Espresso", "Colombia", 23));
    coffeeList.put(2, new Coffee(2, "La Scala Whole Beans", "Bolivia", 23));
    coffeeList.put(3, new Coffee(3, "Dak Lak Filter", "Vietnam", 23));
  }

  public List<Coffee> getAllCoffees(){
    return new ArrayList<>(coffeeList.values());
  }
  public Coffee getCoffeeById(Integer id){
    return coffeeList.get(id);
  }
  public List<Coffee> getRecommendations(Integer id){
        if (id == null) {
            return Collections.emptyList();
        }
        return coffeeList.values().stream()
                .filter(coffee -> !id.equals(coffee.id))
                .limit(2)
                .collect(Collectors.toList());
  }
  @CircuitBreaker(requestVolumeThreshold = 4)
  public Integer getAvailability(Coffee coffee){
    maybeFail();
    return new Random().nextInt(30);
  }

  private void maybeFail() {
    final Long invocationNumber = counter.incrementAndGet();
    if(invocationNumber %4 > 1){
      log.warn("Coffee Service failed");
      throw new RuntimeException("Coffee Service failed");
    }
  }
}

class Coffee{
    public Integer id;
    public String name;
    public String countryOfOrigin;
    public Integer price;
	/**
	 * @param id
	 * @param name
	 * @param countryOfOrigin
	 * @param price
	 */
	public Coffee(Integer id, String name, String countryOfOrigin, Integer price) {
		this.id = id;
		this.name = name;
		this.countryOfOrigin = countryOfOrigin;
		this.price = price;
	}
    
}
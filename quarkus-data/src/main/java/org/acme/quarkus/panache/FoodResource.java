package org.acme.quarkus.panache;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.QueryHint;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.tm.FirstResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;
import io.quarkus.runtime.StartupEvent;
/**
 * 所有组件都在一个文件 里面
 */
@Path("foods")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class FoodResource {
  private static final Logger log = LoggerFactory.getLogger(FoodResource.class.getName());
  void onStart(@Observes StartupEvent ev){
    String[] arrCatalog = new String[]{"tropical", "mountain", "sea", "artifical"};
    Catalog.persist(Arrays.stream(arrCatalog).map(e-> new Catalog(e)).collect(Collectors.toList()));

    Food.persist(new ArrayList<Food>(){{
      add(new Food("Yugirt", LocalDateTime.now(), 3.99, Catalog.find("name", "artifical").firstResult()));
      add(new Food("Beef", LocalDateTime.now(), 33.99, Catalog.find("name", "mountain").firstResult()));
      add(new Food("Bread", LocalDateTime.now(), 13.99, Catalog.find("name", "tropical").firstResult()));
    }});
  }

  @GET
  public List<Food> getAll(){
    return  Food.listAll();
  }

  @GET
  @Path("{keyword}")
  public List<Food> getLike(@PathParam String keyword){
    List<Food> result = null;
    try(Stream<Food> foods = Food.streamAll()){
      result = foods.filter(e->e.getName()
          .toLowerCase().contains(keyword.toLowerCase()))
        .collect(Collectors.toList());
    }
    return result;
  }

  @GET
  @Path("/sort")
  public List<Food> getSorted(){
    return Food.list("order by price desc");
  }

  @GET
  @Path("sql")
  public List<Catalog> getSqlExecution(){
    return Food.find("select catalog from Food").list();
  }

  @GET
  @Path("{id}")
  public Food getById(@PathParam Long id){
    return  Food.findById(id);
  }  

  @POST
  public Response save(Food newFood){
    Food.persist(newFood);
    return Response.ok(newFood).build();
  }
}
@Entity
@Cacheable
class Catalog extends PanacheEntity{
  String name;

  @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL, 
    orphanRemoval = true, fetch = FetchType.EAGER)
  @JsonManagedReference("foods-catalog")
  @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
  List<Food> foods;

/**
 * @return the name
 */
public String getName() {
	return name;
}


/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}


/**
 * @param name
 */
public Catalog(String name, List<Food> foods) {
  this.name = name;
  this.foods = foods;
}


public Catalog(String name) {
  this.name = name;
}


/**
 * 
 */
public Catalog() {
}


/**
 * @return the food
 */
public List<Food> getFood() {
	return foods;
}


/**
 * @param food the food to set
 */
public void setFood(List<Food> foods) {
	this.foods = foods;
}  
  
}

@Entity
@Cacheable
class Food extends PanacheEntity{
  String name;
  @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
  LocalDateTime produce;
  Double price;

  @ManyToOne
  @JsonBackReference("foods-catalog")
  Catalog catalog;

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }
  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * @return the produce
   */
  public LocalDateTime getProduce() {
    return produce;
  }
  /**
   * @param produce the produce to set
   */
  public void setProduce(LocalDateTime produce) {
    this.produce = produce;
  }
  /**
   * @return the price
   */
  public Double getPrice() {
    return price;
  }
  /**
   * @param price the price to set
   */
  public void setPrice(Double price) {
    this.price = price;
  }

  /**
   * 
   */
  public Food() {
  }
/**
 * @return the nutritions
 */
public Catalog getCatalog() {
	return catalog;
}
/**
 * @param nutritions the nutritions to set
 */
public void setCatalog(Catalog catalog) {
	this.catalog = catalog;
}
/**
 * @param name
 * @param produce
 * @param price
 * @param nutritions
 */
public Food(String name, LocalDateTime produce, Double price, Catalog catalog) {
	this.name = name;
	this.produce = produce;
	this.price = price;
	this.catalog = catalog;
}
}
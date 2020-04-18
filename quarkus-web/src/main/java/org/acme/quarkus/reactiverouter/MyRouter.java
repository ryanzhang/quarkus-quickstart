package org.acme.quarkus.reactiverouter;

import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RoutingExchange;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@ApplicationScoped
public class MyRouter {
  @Inject
  MyService service;

  private static final Logger log = LoggerFactory.getLogger(MyRouter.class.getName());
  @Route(path="/hello", methods = HttpMethod.GET)
  void hello(RoutingContext rc){
    rc.response().end("hello");
  }

  @Route(path="/greeting", methods = HttpMethod.GET)
  void greetings(RoutingExchange ex) throws InterruptedException{
    long current = System.currentTimeMillis();
    ex.ok("hello"+ ex.getParam("name").orElse("world"));
    log.info("Running nonblocking router duration:" + 
      (System.currentTimeMillis() - current)/1000);
  }
  @Route(methods = HttpMethod.GET, path = "/greeting-blocking", type = 
    Route.HandlerType.BLOCKING)
  public void blocking(RoutingContext rc) throws InterruptedException{
    long current = System.currentTimeMillis();
    rc.response().end(service.longrunningTask(3));
    log.info("Running blocking router duration:" + 
      (System.currentTimeMillis() - current)/1000);
    
  }

}

@ApplicationScoped
class MyService{
  String longrunningTask(int timout) throws InterruptedException{
    TimeUnit.SECONDS.sleep(timout);
    return "successful";
  }
}
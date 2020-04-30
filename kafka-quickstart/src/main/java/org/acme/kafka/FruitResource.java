package org.acme.kafka;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.smallrye.reactive.messaging.annotations.Merge;

@Path("/")
public class FruitResource {
    private static final Logger log = LoggerFactory.getLogger(FruitResource.class.getName());
  @Inject
  @Channel("fruit-out")
  Publisher<Fruit> fruits;

  @Inject
  @Channel("fruit-in")
  Emitter<Fruit> addFruits;

  @GET
  @Path("/fruits/stream")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @SseElementType("application/json")
  public Publisher<Fruit> fruitStream() {
    log.warn(fruits.toString());
    return fruits;
  }

  @POST
  @Path("/fruits")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addFruit(Fruit fruit) {
    addFruits.send(fruit);
    return Response.ok().build();
  }
}

@ApplicationScoped
class FruitProcessor {

  private static final double CONVERSION_RATE = 0.88;

  @Merge
  @Incoming("fruit-in")
  @Outgoing("fruit-out")
  @Broadcast
  public Fruit process(Fruit fruit) {
    fruit.setPrice(fruit.getPrice() * CONVERSION_RATE);
    return fruit;
  }
}


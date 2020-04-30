package org.acme.kafka;

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
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;


@Path("/")
public class PriceResource {
  @Inject
  @Channel("my-data-stream")
  Publisher<Double> prices;

  @GET
  @Path("/prices/stream")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @SseElementType("text/plain")
  public Publisher<Double> stream(){
    return prices;
  }

}
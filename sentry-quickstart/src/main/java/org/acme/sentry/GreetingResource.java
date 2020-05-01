package org.acme.sentry;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GreetingResource {

  @GET
  @Path("/hello/{name}")
  public String greeting(@PathParam("name") String name){
    if(name.contains("hell")){
      throw new RuntimeException("Go to Hell exception");
    }
    return "hello " + name;
  }

}
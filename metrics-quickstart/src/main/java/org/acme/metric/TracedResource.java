package org.acme.metric;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/hello")
public class TracedResource {
      private static final Logger log = LoggerFactory.getLogger(TracedResource.class.getName());

      @GET
      @Produces(MediaType.TEXT_PLAIN)
      public String hello(){
        log.info("hello");
        return "hello";
      }
}
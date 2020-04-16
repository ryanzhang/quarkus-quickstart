package org.quarkus.example;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {
  private final ObjectMapper mapper;
  public PersonResource(){
    mapper = new ObjectMapper();
  }

  @GET
  @Path("/deserialize")
  public Response JacksonDeserialize() throws JsonMappingException, JsonProcessingException{
    return Response.ok(mapper.readValue("{\"first\":  \"Ryan\", \"lastName\":  \"Zhang\"}", Person.class)).build();
  }

  @GET
  @Path("/serialize")
  public Response normalSJacksonSerialize(){
    return Response.ok(new Person("Li", "Xue")).build();
  }
  
}

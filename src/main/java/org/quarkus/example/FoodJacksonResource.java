package org.quarkus.example;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Path("/food")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FoodJacksonResource {

  @GET
  public Food getFood(){
    return new Food("Apple", 10, "kg");
  }
}

@Data
@AllArgsConstructor
class Food{
  String name;
  Integer weight;
  String unit;
}
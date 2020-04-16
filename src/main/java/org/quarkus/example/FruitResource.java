package org.quarkus.example;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {
    private Set<Fruit> fruits = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    @ConfigProperty(name = "greeting.message")
    String message;

    @ConfigProperty(name="greeting.owner")
    String owner;

    public FruitResource(){
        fruits.add(new Fruit("Apple", "Winter fruit"));
        fruits.add(new Fruit("Pineapple", "Tropical fruit"));
    }
    @GET
    @Path("/fruit")
    @Produces(MediaType.TEXT_PLAIN)
    public Fruit getById(){
      return fruits.iterator().next();
    }

    @GET
    public Set<Fruit> list(){
        return fruits;
    }
    @POST
    public void add(Fruit fruit){
        fruits.add(fruit);
    }
    @DELETE
    public void delete(Fruit fruit){
        fruits.removeIf(existingFruit -> existingFruit.getName().contentEquals(fruit.getName()));
    }
}


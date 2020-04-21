package org.acme.quarkus;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.quarkus.panache.Person;

@Path("")
public class GreetingResource {

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @GET
    @Path("/persons")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> allPerson(){
        return Person.findAllPerson();
    }

    @POST
    @Path("/persons")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response newPerson(Person newPerson){
        Person.persist(newPerson);
        return Response.ok(newPerson).build();
    }

}

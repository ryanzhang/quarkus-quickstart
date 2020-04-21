package org.acme.quarkus;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("fruits")
@ApplicationScoped
@Transactional
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {
    private static final Logger log = LoggerFactory.getLogger(FruitResource.class.getName());
    @Inject
    EntityManager em;

    @GET
    public Fruit[] get() {
        return em.createNamedQuery("Fruits.findAll", Fruit.class)
            .getResultList().toArray(new Fruit[0]);
    }

    @GET
    @Path("{id}")
    public Fruit getSingle(@PathParam Integer id){
        Fruit entity = em.find(Fruit.class, id);
        if(entity == null){
            throw new WebApplicationException("Fruit with id of " + id + 
                " does not existed", 404);
        }
        return entity;
    }

    @POST
    public Response create(Fruit fruit){
        // log.info(fruit.toString());
        System.out.println(fruit.toString());
        if(fruit.getId()!= null){
            throw new WebApplicationException("Id shouldn't be set on request",
                 422);
        }
        em.persist(fruit);
        return Response.ok(fruit).status(201).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam Integer id){
        Fruit entity = em.getReference(Fruit.class, id);
        if(entity == null){
            throw new WebApplicationException("Fruid with id of" + id + 
                " does not exist.", 404 );
        }
        em.remove(entity);
        return Response.status(204).build();
    }
}

@Entity
@NamedQuery(name = "Fruits.findAll", query = "SELECT f FROM Fruit f ORDER BY f.name")
class Fruit {
  @Id
    // @SequenceGenerator(name = "fruitsSequence", sequenceName = "known_fruits_id_seq", allocationSize = 1, initialValue = 10)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fruitsSequence")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @Column(length = 40, unique =true)
  String name;

/**
 * @return the id
 */
public Integer getId() {
	return id;
}

/**
 * @param id the id to set
 */
public void setId(Integer id) {
	this.id = id;
}

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
 * @param id
 * @param name
 */
public Fruit(Integer id, String name) {
	this.id = id;
	this.name = name;
}

/**
 * 
 */
public Fruit() {
}

  
}
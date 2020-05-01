package org.acme.metric;

import java.util.List;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.persistence.Entity;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.StartupEvent;

@Path("/person")
@ApplicationScoped
public class TraceDBResource {
  private static final Random r = new Random();
  @Transactional
  void onStart(@Observes StartupEvent ev){
    for (int i = 0; i < 100; i++) {
      Person.persist(new Person("John" + i , r.nextInt(100)));
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Person> list(){
    return Person.listAll();
  }
}
@Entity
class Person extends PanacheEntity {
  String name;
  int age;

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
   * @return the age
   */
  public int getAge() {
    return age;
  }

  /**
   * @param age the age to set
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * @param name
   * @param age
   */
  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  /**
   * 
   */
  public Person() {
  }
  
  
}
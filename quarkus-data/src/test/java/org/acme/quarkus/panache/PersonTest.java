package org.acme.quarkus.panache;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.acme.quarkus.panache.Person.Status;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class) 
@Transactional
public class PersonTest {
private static final Logger log = LoggerFactory.getLogger(PersonTest.class.getName());

  @Test
  void savePerson(){
    Person person = new Person();
    person.setName("Ryan");
    person.setBirth(LocalDate.of(2010,11,8));
    person.setStauts(Status.ALIVE);
    person.persist();
    List<Person> result = (List<Person>) person.find("name", "Ryan");
    log.info(result.toString());

  }

}
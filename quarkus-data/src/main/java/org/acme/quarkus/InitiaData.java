package org.acme.quarkus;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

import org.acme.quarkus.panache.Person;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class InitiaData {
  @Transactional
  void onStart(@Observes StartupEvent ev){
    Person.persist(new Person("ZhangSan", LocalDateTime.of(2010, 1, 8, 0, 0, 0), Person.Status.HAPPY));
    Person.persist(new Person("LiSi", LocalDateTime.of(2012, 12, 8, 0, 0, 0), Person.Status.HAPPY));
    

  }

}
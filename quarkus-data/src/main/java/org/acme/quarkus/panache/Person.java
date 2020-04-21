package org.acme.quarkus.panache;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.inject.Singleton;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.jackson.ObjectMapperCustomizer;


@Entity
public class Person extends PanacheEntity{
  String name;
  
  /**
   * 
   */
  @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
  LocalDateTime birth;
  Status status;
  
  public static Optional<Person> findByName(String name){
    return find("name", name).firstResultOptional();
  }
  public static List<Person> findAllPerson(){
    return listAll();
  }
  public static enum Status {
    ALIVE,HAPPY,MARRIED;
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
   * @return the birth
   */
  public LocalDateTime getBirth() {
    return birth;
  }


  /**
   * @param birth the birth to set
   */
  public void setBirth(LocalDateTime birth) {
    this.birth = birth;
  }


  /**
   * @return the stauts
   */
  public Status getStauts() {
    return status;
  }


  /**
   * @param stauts the stauts to set
   */
  public void setStauts(Status stauts) {
    this.status = stauts;
  }
  /**
   * @param name
   * @param birth
   * @param stauts
   */
  public Person(String name, LocalDateTime birth, Status stauts) {
    this.name = name;
    this.birth = birth;
    this.status = stauts;
  }
  /**
   * 默认的构造函数是必须的
   */
  public Person() {
  }

}
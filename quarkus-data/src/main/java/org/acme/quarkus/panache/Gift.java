package org.acme.quarkus.panache;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 演示 最普通的JPA entity
 */
@Entity
public class Gift {
  @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "giftSeq")
  private Long id;
  private String name;


  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }
  /**
   * @param id the id to set
   */
  public void setId(Long id) {
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

}
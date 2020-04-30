package org.acme.quarkus.restjson;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Fruit {
  private String name;
  private String description;

  /**
   * @param name
   * @param description
   */
  public Fruit(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * 
   */
  public Fruit() {
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
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }
  

}
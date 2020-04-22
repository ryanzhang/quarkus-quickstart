package org.acme.quarkus.domain;

public class Room {
  private String name;

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
   * @param name
   */
  public Room(String name) {
    this.name = name;
  }

  /**
   * 
   */
  public Room() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */

  @Override
  public String toString() {
    return "Room [name=" + name + "]";
  }
  
}
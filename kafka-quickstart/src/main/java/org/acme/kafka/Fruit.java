package org.acme.kafka;

public class Fruit {

  String name;
  double price;

  public Fruit() {

  }

  /**
   * @param name
   * @param price
   */
  public Fruit(String name, int price) {
    this.name = name;
    this.price = price;
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
   * @return the price
   */
  public double getPrice() {
    return price;
  }

  /**
   * @param price the price to set
   */
  public void setPrice(double price) {
    this.price = price;
  }

}
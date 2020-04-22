package org.acme.quarkus;

public class Person {
    String name;
    int age;
    boolean adult;

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
     * @return the adult
     */
    public boolean isAdult() {
        return adult;
    }

    /**
     * @param adult the adult to set
     */
    public void setAdult(boolean adult) {
        this.adult = adult;
    }
    
    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", adult=" + adult + "]";
    }
}
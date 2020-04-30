package org.acme.quarkus.cdi;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Singleton;

@Singleton
public class Counter{
  private AtomicInteger count =new AtomicInteger(0);

  public int increment(){
    return count.getAndIncrement();
  }
}
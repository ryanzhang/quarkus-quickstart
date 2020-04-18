package org.quarkus.example;

import javax.enterprise.context.ApplicationScoped;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @ApplicationScoped
public class ClassInitialize {
  static {
    System.out.println("Static code initialization");
  }
  public ClassInitialize(){
    log.info("constructor is triggered");
  }
}
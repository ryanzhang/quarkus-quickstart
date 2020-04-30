package org.acme.quarkus.health;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.health.HealthCheckResultBuilder;
import org.apache.camel.microprofile.health.AbstractCamelMicroProfileLivenessCheck;

public class CustomLivenessCheck extends AbstractCamelMicroProfileLivenessCheck {

  AtomicInteger hitCount = new AtomicInteger();

  public CustomLivenessCheck (){
    super("custom-liveness-check");
    getConfiguration().setEnabled(true);
  }

  @Override
  protected void doCall(HealthCheckResultBuilder builder, Map<String, Object> options) {
    int hits = hitCount.incrementAndGet();
    if(hits%5 == 0){
      builder.down();
    }else{
      builder.up();
    }
  }

}
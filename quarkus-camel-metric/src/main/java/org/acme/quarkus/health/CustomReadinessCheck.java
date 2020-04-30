package org.acme.quarkus.health;

import java.util.Map;

import org.apache.camel.health.HealthCheckResultBuilder;
import org.apache.camel.microprofile.health.AbstractCamelMicroProfileReadinessCheck;

public class CustomReadinessCheck extends AbstractCamelMicroProfileReadinessCheck {

  public CustomReadinessCheck() {
    super("custom-readiness-check");
    getConfiguration().setEnabled(true);
  }

  @Override
  protected void doCall(HealthCheckResultBuilder builder, Map<String, Object> options) {
    // TODO Auto-generated method stub
    builder.up();

  }

}
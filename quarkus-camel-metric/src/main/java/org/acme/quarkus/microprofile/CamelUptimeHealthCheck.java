package org.acme.quarkus.microprofile;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class CamelUptimeHealthCheck implements HealthCheck {

  @Inject
  CamelContext camelContext;

  @Override
  public HealthCheckResponse call() {
    HealthCheckResponseBuilder builder = HealthCheckResponse.named("Uptime readiness check");
    if(camelContext.getUptimeMillis()>0){
      builder.up();
    } else{
      builder.down();
    }
    return builder.build();
  }


}
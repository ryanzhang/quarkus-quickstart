package org.acme.quarkus.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class TimerRouter extends RouteBuilder{

  @ConfigProperty(name = "timer.period", defaultValue = "1000")
  String period;

  @Inject
  Counter counter;

  @Override
  public void configure() throws Exception {
    fromF("timer:foo?period=%s", period)
      .setBody(exchange -> "增加 counter:" + counter.increment())
      .to("log:cdi-example?showExchangePattern=true&showBodyType=true");
  }
}
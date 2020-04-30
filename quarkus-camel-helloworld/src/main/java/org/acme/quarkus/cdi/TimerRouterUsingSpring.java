package org.acme.quarkus.cdi;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@ApplicationScoped
public class TimerRouterUsingSpring extends RouteBuilder{

  @Value("timer.period")
  String period = "1000";

  @Autowired
  Counter counter;

	@Override
	public void configure() throws Exception {
    fromF("timer:foo?period=%s", period)
      .setBody(exchange -> "增加 counter:" + counter.increment())
      .to("log:cdi-example-withspringdi?showExchangePattern=true&showBodyType=true");
  }
}

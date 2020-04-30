package org.acme.quarkus;

import org.apache.camel.builder.RouteBuilder;

public class Routes extends RouteBuilder{

  @Override
  public void configure() throws Exception {
    from("timer:greeting?period=1000")
      .to("netty-http:http://localhost:8099/greeting");

    from("netty-http:http://0.0.0.0:8099/greeting")
      .delay(simple("${random(1000,5000)}"))
      .setBody(constant("Hello From Camel Quarkus!"));
  }

}
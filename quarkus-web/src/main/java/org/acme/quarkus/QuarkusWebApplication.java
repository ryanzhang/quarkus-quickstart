package org.acme.quarkus;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
  tags = {
    @Tag(name="widget", description = "Widget operations"),
    @Tag(name ="gasket", description = "Operations related to gskets")
  },
  info = @Info(
    title = "Quarkus Web API",
    version = "1.0.1",
    contact = @Contact(
      name = "Ryan @ Redhat",
      url = "http://www.awesome.com",
      email = "ryan@redhat.com"),
    license = @License(
        name = "Apache 2.0",
        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
      )
    )
)
public class QuarkusWebApplication  extends Application{

}

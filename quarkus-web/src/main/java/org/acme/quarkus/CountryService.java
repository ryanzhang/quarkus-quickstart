package org.acme.quarkus;

import java.util.Set;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/v2")
@RegisterRestClient
@Produces(MediaType.APPLICATION_JSON)
public interface CountryService {
  @GET
  @Path("/name/{name}")
  Set<Country> getByName(@PathParam String name);

  @GET
  @Path("name/{name}")
  CompletionStage<Set<Country>> getByNameAsync(@PathParam String name);

  // @GET
  // @Path("name/{name}")
  // Uni<Set<Country>> getByNameAsUni(@PathParam String name);
}


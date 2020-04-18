package org.acme.quarkus;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;



@Path("/country")
@Produces(MediaType.APPLICATION_JSON)
public class CountryResource {

  @Inject
  @RestClient
  CountryService countryService;

  @GET
  @Path("/name/{name}")
  public Set<Country> name(@PathParam String name) {
      return countryService.getByName(name);
  }  
  @GET
  @Path("name-async/{name}")
  public CompletionStage<Set<Country>> nameAsync(@PathParam String name) {
    return countryService.getByNameAsync(name);
  }
}

@JsonIgnoreProperties(ignoreUnknown = true)
 class Country{
  public String name;
  public String alpha2Code;
  public String capital;
  public List<Currency> currencies;

  static class Currency {
      public String code;
      public String name;
      public String symbol;
  }
}
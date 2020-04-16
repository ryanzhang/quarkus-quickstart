package org.quarkus.example;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.jsonb.JsonbConfigCustomizer;
import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Path("/breakfast")
public class BreakfastResource {

  @Inject
  Breakfast breakfast;
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Breakfast allfood() {
    return breakfast;
  }
}

// @Data
//@ApplicationScoped
@RequestScoped
class Breakfast{
   Map<Integer, String> list = new HashMap<Integer, String>(){{
     put(1, "egg");
     put(2, "soy milk");
     put(3, "orange");
     put(8, "pineapple");
   }};

  Map<Integer, String> getList() {
    return this.list;
  }

  void setList(Map<Integer, String> list) {
    this.list = list;
  }

  public String toString() {
    return "Breakfast(list=" + this.getList() + ")";
  }
}



package org.acme.quarkus;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.junit.QuarkusTest;

// import lombok.extern.slf4j.Slf4j;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CountryResourceTest {
  private static final Logger log = LoggerFactory.getLogger(CountryResourceTest.class.getName());
  @Test
  void testCountryAsyncEndpoint(){
    given()
      .when().get("/country/name-async/greece")
      .then()
      .statusCode(200)
      .log().body()
      .body("$.size()", is(1),
        "[0].alpha2Code", is("GR"),
        "[0].capital", is("Athens"),
        "[0].currencies.size()", is(1),
        "[0].currencies[0].name", is("Euro"));
  }
  @Test
  void testCountrySyncEndpoint(){
    given()
      .when().get("/country/name/greece")
      .then()
      .statusCode(200)
      .body("$.size()", is(1),
        "[0].alpha2Code", is("GR"),
        "[0].capital", is("Athens"),
        "[0].currencies.size()", is(1),
        "[0].currencies[0].name", is("Euro"));
  }  


}
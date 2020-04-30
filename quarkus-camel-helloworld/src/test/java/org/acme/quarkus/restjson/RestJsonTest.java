package org.acme.quarkus.restjson;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;

@QuarkusTest
public class RestJsonTest {

  @Test
  void getFruitsEndpoint(){
    given().when()
      .then()
      .statusCode(200)
      .body("$.size", is(2),
            "name", containsInAnyOrder("Apple", "Pineapple"),
            "description", containsInAnyOrder("Winter fruit", "Tropical fruit")
      );

      given()
        .body("{\"name\": \"pear\", \"description\": \"Winter fruit\"}")
        .header("Content-Type", "application/json")
        .when()
        .post("/fruits")
        .then()
        .statusCode(200)
        .body(
          "$.size", is(3),
          "description", containsInAnyOrder("Winter fruit", "Tropical fruit", "Winter fruit")
        );
    
      given().when()
        .get("/legumes")
        .then()
        .statusCode(200)
        .body("$.size()", is(2),
              "name", containsInAnyOrder("Carrot", "Zucchini"),
              "description", containsInAnyOrder("Root vegetable, usually orange", "Summer squash")
        );
  }
}
package org.acme.quarkus;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.MatcherAssert.*;
@QuarkusTest
public class BookResourceTest {
  private static final Logger log = LoggerFactory.getLogger(BookResourceTest.class.getName());

  @Test
  void testGetBooks(){
    given().when()
    .get("/books")
    .then()
    .statusCode(200)
    .assertThat()
    .body("$", hasSize(2),
          "[0].author", equalTo("John"),
          "[1].author", equalTo("Great Sun")
    );
  }
  @Test
  void testGetBook(){
    given().when()
      .get("/books/John")
      .then()
      .statusCode(200)
      .body("author", equalTo("John"),
            "title", equalTo("7 Great habits")
      );
  }

  @Test
  void addBook(){
    given()
      .body("{\"title\": \"iPhone\", \"author\": \"Steven\", \"pages\": 800}")
      .header("Content-Type", MediaType.APPLICATION_JSON)
      .when()
      .post("/books/manual-validation")
      .then()
      .statusCode(200)
      .body("success", is(true));
  }
  @Test
  void deleteBookByAuthor(){
    given()
      .when()
      .delete("/books/john")
      .then()
      .statusCode(200)
      .body("success", is(true));
  }
  @Test
  void getSwaggerUI(){
    given()
      .when()
      .get("/openapi")
      .then()
      .statusCode(200);
    given()
      .when()
      .get("/swagger-ui")
      .then()
      .statusCode(200);
  }  
}
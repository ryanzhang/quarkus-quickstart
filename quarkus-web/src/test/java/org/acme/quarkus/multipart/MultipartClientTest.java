package org.acme.quarkus.multipart;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class MultipartClientTest {

  @Test
  void echoEndpoint() throws InterruptedException{
    assertTrue(
      given().log().all()
        .when()
        .post("/client/echo")
        .then()
        .statusCode(200)
        .extract().body().asString().contains("greeting.txt"));
  }
  @Test
  void saveEndpoint(){
    assertTrue(
      given()
      .when()
      .post("/client/save")
      .then()
      .log().body()
      .statusCode(200)
      .extract().body().asString().contains("successfully uploaded"));
  }
}
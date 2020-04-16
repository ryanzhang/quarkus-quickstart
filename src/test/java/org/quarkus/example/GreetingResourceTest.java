package org.quarkus.example;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.net.URL;

@QuarkusTest
public class GreetingResourceTest {
    // @TestHTTPResource("/myservlet")
    // URL testUrl;

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/greeting")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }
    @Test
    void testBreakfastEndpoint() {
        given()
          .when().get("/breakfast")
          .then()
          .statusCode(200);
    }

}
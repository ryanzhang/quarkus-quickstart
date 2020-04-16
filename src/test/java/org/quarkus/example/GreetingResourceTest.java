package org.quarkus.example;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@QuarkusTest
@Slf4j
public class GreetingResourceTest {
    @TestHTTPResource("/index.html")
    URL url;

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

    @Test
    public void testIndexHtml() throws IOException, URISyntaxException {
      assertNotNull(url);
      log.info(url.toURI().getHost());
      try (InputStream in = url.openStream()){
        String contents = readStream(in);
        log.info(contents);
        assertTrue(contents.contains("<title>quickstart</title>"));
      }
    }

    private static String readStream(InputStream in) throws IOException {
      byte[] data = new byte[1024];
      int r;
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      while((r= in.read(data))>0){
        out.write(data, 0, r);
      }
      return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }

}
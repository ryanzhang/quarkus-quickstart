package org.quarkus.example;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.NativeImageTest;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.*;
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
import java.io.File;
import static org.hamcrest.Matcher.*;

@NativeImageTest
@Slf4j
public class NativeGreetingResourceIT extends GreetingResource {

    @Test
    void loadResourceFile(){
        given()
            .when().get("/config")
            .then()
            .log().body()
            .statusCode(200).assertThat()
            .body("$.name", is("h2"),
                "$.url", is("jdbc://localhost")
            );
    }
}
package org.acme.quarkus.security;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@QuarkusTest
public class EncryptJWTToken {
  private static final Logger log = LoggerFactory.getLogger(EncryptJWTToken.class.getName());
  @ConfigProperty(name ="smallrye.jwt.sign.key-location")
  String privateKeyLocation;

  @Test
  void generateClaim(){
    JwtClaimsBuilder builder1 = Jwt.claims();
    builder1.claim("customClaim", "custom-value").issuer("https://awesome.com");
    assertNotNull(privateKeyLocation);
    
    String token = builder1.sign();
    assertNotNull(token);
    log.info(builder1.sign());
  }

  @Test
  void generateTokenMultipleClaim(){
    String token = Jwt.claims()
    .claim("name", "张三")
    .claim("gender", "女")
    .claim("rank", "vip")
    .sign();
    log.info(token);
    assertNotNull(token);
  }  
  @Test
  void generateTokenFromJson(){
    String token = Jwt.claims("/JwtClaims.json")
    .sign();
    String decodeToken = new String(Base64.decodeBase64(token));
    log.info(token);
    log.info(decodeToken);
    
    assertNotNull(token);
    assertAll(decodeToken, 
    ()-> assertTrue(decodeToken.contains("sub")),
    ()-> assertTrue(decodeToken.contains("preferred_username")));

    assertThat(decodeToken, containsString("jdoe@quarkus.io"));
  }

  @Test
  void generateEncrptToken(){
    String token = Jwt.claims("/JwtClaims.json")
    .jwe()
    .header("customer-header", "custom-value")
    .encrypt();

    String decodeToken = new String(Base64.decodeBase64(token));

    
    log.info("Check the token {} {}", token, decodeToken);
  }
}
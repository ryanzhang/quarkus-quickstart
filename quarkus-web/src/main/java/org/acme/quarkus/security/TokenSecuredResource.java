package org.acme.quarkus.security;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonString;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/secured")
@RequestScoped
@Produces(MediaType.TEXT_PLAIN)
public class TokenSecuredResource {
  private static final Logger log = LoggerFactory.getLogger(TokenSecuredResource.class.getName());
  @Inject
  JsonWebToken jwt;

  @GET
  @Path("permit-all")
  @PermitAll
  public String hello(@Context SecurityContext ctx ){
    Principal caller = ctx.getUserPrincipal();
    String name = caller == null ? "anonymous" : caller.getName();
    boolean hasJWT = jwt.getClaimNames() != null;
    String reply = String.format("hello + %s, isSecure: %s, authScheme: %s, hasJWT: %s", name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJWT);
    log.info(reply);
    log.info(jwt.toString());
    return reply;
  }

  @GET
  @Path("roles-allowed")
  @RolesAllowed({"Echoer", "Subscriber"})
  public String helloRolesAllowed(@Context SecurityContext ctx){
    Principal caller = ctx.getUserPrincipal();
    String name = caller == null ? "anonymous" : caller.getName();
    boolean hasJWT = jwt.getClaimNames() != null;
    String reply = String.format("hello + %s, isSecure: %s, authScheme: %s, hasJWT: %s", name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJWT);
    log.info(caller.getClass().getName() +caller.toString());
    log.info(reply);
    log.info(jwt.getClass().getName() + jwt.toString());
    return reply;

  }

  @GET
  @Path("winners")
  @RolesAllowed("Subscriber")
  public String winners(){
    int remaining = 6;
    ArrayList<Integer> numbers = new ArrayList<>();
    if(jwt.containsClaim(Claims.birthdate.name())){
      String bdayString = jwt.getClaim(Claims.birthdate.name());
      LocalDate bday = LocalDate.parse(bdayString);
      numbers.add(bday.getDayOfMonth());
      remaining --;
    }
    while(remaining>0){
      int pick = (int) Math.rint(64*Math.random() + 1);
      numbers.add(pick);
      remaining --;
    }
    return numbers.toString();

  }
  @Inject
  @Claim
  Optional<JsonString> birthdate;

  @GET
  @Path("winners2")
  @RolesAllowed("Subscriber")
  public String winners2() {
      int remaining = 6;
      ArrayList<Integer> numbers = new ArrayList<>();

      // If the JWT contains a birthdate claim, use the day of the month as a pick
      if (birthdate.isPresent()) { 
          String bdayString = birthdate.get().getString(); 
          LocalDate bday = LocalDate.parse(bdayString);
          numbers.add(bday.getDayOfMonth());
          remaining --;
      }
      // Fill remaining picks with random numbers
      while(remaining > 0) {
          int pick = (int) Math.rint(64 * Math.random() + 1);
          numbers.add(pick);
          remaining --;
      }
      return numbers.toString();
  }
}
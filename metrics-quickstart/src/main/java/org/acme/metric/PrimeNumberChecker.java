package org.acme.metric;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
// import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/")
public class PrimeNumberChecker {
  private long highestPrimeNumberSoFar =2;

  @GET
  @Path("/{number}")
  @Produces(MediaType.TEXT_PLAIN)
  @Counted(
    name = "performedChecks",
    description = "质数的访问次数"
  )
  @Timed(
    name = "checksTimer",
    description = "质数接口访问时长"
  )
  public String checkIfPrime(@PathParam long number){
    if(number <1 ){
      return "质数必须是自然数(大于零)";
      
    }
    if(number ==1 ){
      return "1 不是质数";
    }
    if(number == 2)
      return "2 是质数";
    
    if(number % 2 == 0){
      return number + " 不是质数";
    }

    for(int i = 3; i < Math.floor(Math.sqrt(number)) +1; i = i + 2){
      if(number % i == 0){
        return number + " 不是质数, 可以被 " + i + "整除";
      }
    }
    if (number > highestPrimeNumberSoFar) {
      highestPrimeNumberSoFar = number;
      
    }
    return number + " 是质数";
  }
  @Gauge(
    name = "highestPrimeNumberSoFar",
    description = "目前最大的质数",
    unit = MetricUnits.NONE
  )
  public long highestPrimeNumberSoFar(){
    return highestPrimeNumberSoFar;
  }
}
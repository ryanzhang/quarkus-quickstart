package org.quarkus.example;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.HttpRequest;

import io.vertx.core.http.HttpServerRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class LoggerFilter implements ContainerRequestFilter{
  
  @Context
  UriInfo uriInfo;

  @Context
  HttpServerRequest request;

  @Context
  HttpRequest httpRequest;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
    final String method = requestContext.getMethod();
    final String path = uriInfo.getPath();
    final String address = request.remoteAddress().toString();
    final String ipaddress = httpRequest.getRemoteAddress();
    final String host = httpRequest.getRemoteHost();
    log.info("Request {} {} from IP {} {} {}", method, path, address, ipaddress, host);
	}

}
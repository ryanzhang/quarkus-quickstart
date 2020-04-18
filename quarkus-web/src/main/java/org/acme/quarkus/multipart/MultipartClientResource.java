package org.acme.quarkus.multipart;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/client")
public class MultipartClientResource {

  @Inject
  @RestClient
  MultipartService service;

  @POST
  @Path("/echo")
  @Produces(MediaType.TEXT_PLAIN)
  public String echoFile() {
    MultipartBody body = new MultipartBody();
    body.fileName= "greeting.txt";
    body.file = new ByteArrayInputStream("HELLO WORLD".getBytes(StandardCharsets.UTF_8));
    return service.sendMultipartData(body);
  }

  @POST
  @Path("/save")
  @Produces(MediaType.TEXT_PLAIN)
  public String saveFile() {
    MultipartBody body = new MultipartBody();
    body.fileName= "greeting.txt";
    body.file = new ByteArrayInputStream("HELLO WORLD".getBytes(StandardCharsets.UTF_8));
    return service.saveMultipartData(body);
  }  
}
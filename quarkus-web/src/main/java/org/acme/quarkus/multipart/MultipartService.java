package org.acme.quarkus.multipart;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@RegisterRestClient
@Path("/")
public interface MultipartService {
  @POST
  @Path("/echo")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.TEXT_PLAIN)
  String sendMultipartData(@MultipartForm MultipartBody data);

  @POST
  @Path("/save")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.TEXT_PLAIN)
  String saveMultipartData(@MultipartForm MultipartBody data);

}
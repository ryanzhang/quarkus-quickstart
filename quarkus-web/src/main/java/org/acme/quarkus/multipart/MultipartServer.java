package org.acme.quarkus.multipart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.jaxrs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;


@Path("/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.TEXT_PLAIN)
public class MultipartServer {

  @Path("/echo")
  @POST
  public String echo(String requestBody){
    // log.info(requestBody);
    return requestBody;
  }

  @Path("/save")
  @POST
  public String saveFile(@MultipartForm MultipartBody mbody ) throws IOException{
    String filePath = "/tmp/" + mbody.fileName;
    FileOutputStream out = new FileOutputStream(new File(filePath));
    int read =0;
    byte[] bytes = new byte[1024];
    while((read = mbody.file.read(bytes))!=-1){
      out.write(bytes, 0, read);
    }
    out.flush();
    out.close();
    return "File successfully uploaded to : " + filePath;
  }
}
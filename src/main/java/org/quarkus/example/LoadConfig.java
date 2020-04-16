package org.quarkus.example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;

@Path("/config")
public class LoadConfig {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String showConfigContent() throws IOException{
    InputStream config_inputstream = getClass().getClassLoader().getResourceAsStream("database-config.json");
    return IOUtils.toString(config_inputstream, StandardCharsets.UTF_8.name());
  }
  @GET
  @Path("/xml")
  @Produces(MediaType.APPLICATION_XML)
  public String showXmlConfigContent() throws IOException{
    InputStream config_inputstream = getClass().getClassLoader().getResourceAsStream("another-config.xml");
    return IOUtils.toString(config_inputstream, StandardCharsets.UTF_8.name());
  }
}
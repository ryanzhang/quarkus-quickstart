package org.acme.quarkus;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.commons.configuration.XMLStringConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.infinispan.client.Remote;
import io.quarkus.runtime.StartupEvent;
@ApplicationScoped
class Initialize{
  private static final Logger log = LoggerFactory.getLogger(InfinispanClientDemo.class.getName());
  private static final String CACHE_CONFIG = "<infinispan><cache-container>"
      + "<distributed-cache name=\"%s\"></distributed-cache>" + "</cache-container>" + "</infinispan>";

  @Inject
  RemoteCacheManager cacheManager;

  void onStart(@Observes StartupEvent ev) {
    log.info("创建或者获取 infinispan 服务器的mycache 默认配置");
    RemoteCache<Object, Object> cache = cacheManager.administration().getOrCreateCache("mycache",
        new XMLStringConfiguration(String.format(CACHE_CONFIG, "mycache")));
    cache.put("hello", "Hello world, Infinispan is Up");
  }
}

@Path("/greeting")
// @ApplicationScoped
public class InfinispanClientDemo {


  @Inject @Remote("mycache")
  RemoteCache<String, String> cache;

  @GET
  @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
  public Response hello(){
    return Response.ok( cache.get("hello")).build();
  }

}
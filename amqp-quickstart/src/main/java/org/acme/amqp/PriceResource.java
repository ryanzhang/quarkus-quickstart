package org.acme.amqp;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import io.smallrye.reactive.messaging.annotations.Channel;

@Path("/prices")
public class PriceResource {
  /**
   * z这里出现了一个新的对象，就是Publisher 这个可以理解是channel上面的一个暴露器，数据就是
   * 要通过Publisher 输出，或者获取,与之对应的是Emmiter类型 它可以执行send命令
   */
  @Inject
  @Channel("my-data-stream")
  Publisher<Double> prices;

  /**
   * 这里出现了MediaType.SERVER_SEND_EVENTS类型的传输协议, 俗称SSE 这个是专门针对Stream
   * 的，它是一种长连接 不同与普通的http方式
   * @return
   */
  @GET
  @Path("/stream")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @SseElementType(MediaType.TEXT_PLAIN)
  public Publisher<Double> stream(){
    return prices;
  }

}
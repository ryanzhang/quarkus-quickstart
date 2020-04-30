package org.acme.amqp;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.annotations.Broadcast;

@ApplicationScoped
public class PriceGenerator {
  private Random random = new Random();

  /**
   * 使用RX中的Flowable产生流
   * 输出到Artimis 的 generated-price Channel中
   * 这里主要学习使用Flowable以及Outgoing注解
   * Outgoing指向的是channel名字 channel可以理解为topic的连接器，既有outgoing 端，也有incoming端
   * @return
   */
  @Outgoing("generated-price")
  public Flowable<Integer> generate(){
    return Flowable.interval(5, TimeUnit.SECONDS)
            .map(tick -> random.nextInt(100));
  }

}
@ApplicationScoped
class PriceConverter {
  private static final double CONVERSION_RATE=0.88;

  /**
   * Incoming 就是从哪个channel获取数据, 
   * Outgoing, 这里连接的是memory中的Channel, 也就是从消息中间件的topic中获得数据，然后写到
   * in-memory的channel中
   * Broadcase 这个是广播的方式，就可以1对多
   */
  @Incoming("prices")
  @Outgoing("my-data-stream")
  @Broadcast
  public double process(int priceInUsed){
    return priceInUsed * CONVERSION_RATE;
  }
}
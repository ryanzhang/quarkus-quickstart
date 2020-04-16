package org.quarkus.example;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import io.quarkus.runtime.LaunchMode;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;

@QuarkusTest
@Slf4j
public class ConfigTest {

  @ConfigProperty(name = "greeting.message")
  String message;

  /**
   * test目录下application.properties 和src下的application.properties 只取距离最近的一个，不会融合.
   */
  @ConfigProperty(name = "quarkus.http.port")
  Integer port;

  @ConfigProperty(name ="notdefined.message")
  Optional<String> notdefinedMsg;

  @Test
  void fetchProperty(){
    assertThat(message).isNotEmpty();
    log.info(message);
    assertThat(port).isEqualTo(8100);

    assertThat(notdefinedMsg).isEmpty();

    String greeting_message = ConfigProvider.getConfig().getValue("greeting.message", String.class);
    assertThat(greeting_message).containsIgnoringCase("quarkus test");

    Optional<String> maybeMessage = ConfigProvider.getConfig().getOptionalValue("notexist.message", String.class);
    assertThat(maybeMessage).isEmpty();

  }

  @Test
  void printLogger(){
    log.info(log.toString());
  }
  @Test
  void checkLaunchMode(){
    assertThat(LaunchMode.current().name()).isEqualToIgnoringCase("test");
    log.info(LaunchMode.current().name());
  }
}
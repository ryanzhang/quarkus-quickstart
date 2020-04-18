package org.quarkus.example;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@QuarkusTest
@Slf4j
public class LifecycleTest {

    @Test
    void contextLoaded(){

    }
}

@Slf4j
@ApplicationScoped
class LFBean{
    void onStart(@Observes StartupEvent event){
      log.info("Application is running" + event.toString());
    }
    void onStop(@Observes ShutdownEvent event){
        log.info("Application is stopping" + event.toString());
    }
}
package org.quarkus.example;

import io.quarkus.arc.AlternativePriority;
import io.quarkus.arc.DefaultBean;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.annotation.Priority;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.*;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Set;

@Slf4j
@QuarkusTest
public class DITest {
    @Inject BeanManager beanManager;

    @Inject
    @MessageTransport("email")
    MessageSender messageSender;

    @Inject
    @MessageTransport("sms")
    MessageSender smsMessageSender;

    @Any
    Instance<MessageSender> messageSenders;

    @Test
    void getAllBeans(){
        assertNotNull(beanManager);

        Set<Bean<?>> beans = beanManager.getBeans(Object.class, new AnnotationLiteral<Any>() {} );

        log.info("All beans amounts: " + beans.size());
//        beans.forEach(e-> System.out.println(e.getBeanClass().getName() + ":"  + e.getQualifiers() + ":" + e.getScope()));
        beans.forEach(e-> {
            if (e.getBeanClass().getName().startsWith("org.quarkus.example"))
                System.out.println(e.getBeanClass().getName() + ":" + e.toString() + ":" + e.getQualifiers() + ":" + e.getScope());
        });
//        assertThat(beans).anyMatch(e->e.get("getMessageSender"));
        assertThat(messageSender).isNotNull();
        assertThat(messageSender.sendMessage()).contains("Sending email");

        assertThat(smsMessageSender).isNotNull();
        assertThat(smsMessageSender.sendMessage()).contains("Sending sms");

        assertThat(messageSenders).isNotNull();
        assertThat(messageSenders).hasSize(2);
        messageSenders.stream().forEach(e->e.sendMessage());
//        messageSenders.forEach(e->e.sendMessage());
    }

}

@SessionScoped
class MessageSenderFactory{
    @Produces
    @MessageTransport("email")
    MessageSender getEmailMessageSender(){
        return new EmailMessageSender();
    }

    @Produces
    @MessageTransport("sms")
    MessageSender getSMSMessageSender(){
        return new SMSMessageSender();
    }

//    @Produces
//    @AlternativePriority(100)
//    @MessageTransport("email")
//    MessageSender getMockEmailMessageSender(){
//        return new MockEmailMessageSender();
//    }

}

interface MessageSender{
    String sendMessage();
}

class EmailMessageSender implements MessageSender{

    @Override
    public String sendMessage() {
        String text ="Sending email message!";
        System.out.println(text);
        return text;
    }
}
class SMSMessageSender implements MessageSender{

    @Override
    public String sendMessage() {
        String text ="Sending sms message!";
        System.out.println(text);
        return text;
    }
}

class MockEmailMessageSender implements MessageSender{
    @Override
    public String sendMessage() {
        String text ="Mock Sending email message!";
        System.out.println(text);
        return text;
    }
}
@Qualifier
@Retention(RUNTIME)
@Target({FIELD, TYPE, METHOD})
@interface MessageTransport {

    String value();

}
package com.power.usercmdsvc.messages;

import com.power.usercore.events.UserRegisteredEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Supplier;

@Component
public class MessageSupplier {

    private Boolean produce;

    public MessageSupplier(@Value("${spring.cloud.stream.producer.produce}") Boolean produce) {
        this.produce = produce;
    }

    @Bean
    public Supplier<UserRegisteredEvent> userRegistered() {
        return () -> {
            if(produce) {
                return getPayload();
            }
            return null;
        };
    }

    private UserRegisteredEvent getPayload() {
        return UserRegisteredEvent
                .builder()
                .id(UUID.randomUUID().toString())
                .firstName("Yauri")
                .lastName("Attamimi")
                .username("yauritux")
                .email("yauritux@gmail.com")
                .build();
    }
}

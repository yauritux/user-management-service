package com.power.usercmdsvc.messages;

import com.power.usercore.events.UserRegisteredEvent;
import com.power.usercore.events.UserUpdatedEvent;
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

    @Bean("userRegistered")
    public Supplier<UserRegisteredEvent> userRegistered() {
        return () -> {
            if(produce) {
                return getUserRegistrationPayload();
            }
            return null;
        };
    }

    @Bean("userUpdated")
    public Supplier<UserUpdatedEvent> userUpdated() {
        return () -> {
            if(produce) {
                return getUserUpdatePayload();
            }
            return null;
        };
    }

    private UserRegisteredEvent getUserRegistrationPayload() {
        return UserRegisteredEvent
                .builder()
                .id(UUID.randomUUID().toString())
                .firstName("Yauri")
                .lastName("Attamimi")
                .username("yauritux")
                .email("yauritux@gmail.com")
                .build();
    }

    private UserUpdatedEvent getUserUpdatePayload() {
        return UserUpdatedEvent
                .builder()
                .id(UUID.randomUUID().toString())
                .firstName("M Yauri M")
                .lastName("Attamimi")
                .username("yauritux")
                .email("yauritux@gmail.com")
                .build();
    }
}

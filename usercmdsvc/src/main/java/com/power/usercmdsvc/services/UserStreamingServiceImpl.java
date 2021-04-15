package com.power.usercmdsvc.services;

import com.power.usercore.events.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserStreamingServiceImpl implements UserStreamingService {

    private static final String PRODUCER_BINDING_NAME = "userRegistered-out-0";
    private final StreamBridge streamBridge;

    public UserStreamingServiceImpl(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void publishUserRegisteredEvent(UserRegisteredEvent event) {
        boolean sent = streamBridge.send(PRODUCER_BINDING_NAME, event);
        if(!sent) {
            log.error("Error publishing UserRegisteredEvent to our Exchange...");
        }
    }
}

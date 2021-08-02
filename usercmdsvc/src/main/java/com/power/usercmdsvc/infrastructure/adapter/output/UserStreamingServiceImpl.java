package com.power.usercmdsvc.infrastructure.adapter.outputs;

import com.power.usercmdsvc.domain.service.port.output.UserStreamingService;
import com.power.usercore.domain.events.UserRegisteredEvent;
import com.power.usercore.domain.events.UserUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserStreamingServiceImpl implements UserStreamingService {

    private static final String USER_REGISTRATION_CHANNEL = "userRegistered-out-0";
    private static final String USER_UPDATE_CHANNEL = "userUpdated-out-0";
    private final StreamBridge streamBridge;

    public UserStreamingServiceImpl(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void publishUserRegisteredEvent(UserRegisteredEvent event) {
        boolean sent = streamBridge.send(USER_REGISTRATION_CHANNEL, event);
        if(!sent) {
            log.error("Error publishing UserRegisteredEvent to our Exchange...");
        }
    }

    @Override
    public void publishUserUpdatedEvent(UserUpdatedEvent event) {
        boolean sent = streamBridge.send(USER_UPDATE_CHANNEL, event);
        if(!sent) {
            log.error("Error publishing UserUpdatedEvent to our Exchange...");
        }
    }
}

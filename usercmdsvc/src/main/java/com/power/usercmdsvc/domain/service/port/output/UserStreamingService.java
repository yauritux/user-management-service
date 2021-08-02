package com.power.usercmdsvc.domain.service.port.output;

import com.power.usercore.domain.events.UserRegisteredEvent;
import com.power.usercore.domain.events.UserUpdatedEvent;

public interface UserStreamingService {
    void publishUserRegisteredEvent(UserRegisteredEvent event);
    void publishUserUpdatedEvent(UserUpdatedEvent event);
}

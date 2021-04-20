package com.power.usercmdsvc.services;

import com.power.usercore.events.UserRegisteredEvent;
import com.power.usercore.events.UserUpdatedEvent;

public interface UserStreamingService {
    void publishUserRegisteredEvent(UserRegisteredEvent event);
    void publishUserUpdatedEvent(UserUpdatedEvent event);
}

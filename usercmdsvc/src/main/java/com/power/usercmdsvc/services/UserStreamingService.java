package com.power.usercmdsvc.services;

import com.power.usercore.events.UserRegisteredEvent;

public interface UserStreamingService {
    void publishUserRegisteredEvent(UserRegisteredEvent event);
}

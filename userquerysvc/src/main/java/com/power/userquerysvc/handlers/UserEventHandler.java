package com.power.userquerysvc.handlers;

import com.power.usercore.events.UserRegisteredEvent;

public interface UserEventHandler {
    void on(UserRegisteredEvent event);
}

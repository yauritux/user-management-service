package com.power.userquerysvc.infrastructure.api.message.handlers;

public interface UserEventHandler {
    String EVENT_HANDLER_PACKAGE = UserEventHandler.class.getPackageName();

    void handle();
}

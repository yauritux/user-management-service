package com.power.userquerysvc.handlers;

public interface UserEventHandler {
    String EVENT_HANDLER_PACKAGE = UserEventHandler.class.getPackageName();

    void handle();
}

package com.power.usercore.events;

import lombok.Data;

import java.util.Date;

@Data
public class BaseEvent {

    private String eventType;
    private String eventHandler;
    private Date timestamp;

    public BaseEvent() {
        this.eventType = this.getClass().getName();
        this.eventHandler = this.getClass().getSimpleName() + "Handler";
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        return this.getEventType();
    }
}

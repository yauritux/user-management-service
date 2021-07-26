package com.power.usercmdsvc.commands.handlers;

import com.power.usercmdsvc.domain.entities.EmailLookupEntity;
import com.power.usercmdsvc.domain.repositories.EmailLookupRepository;
import com.power.usercmdsvc.port.output.service.UserStreamingService;
import com.power.usercore.events.UserRegisteredEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("emailEntity")
public class UserEventHandler {

    @EventHandler
    public void on(UserRegisteredEvent event, EmailLookupRepository emailLookupRepository,
            UserStreamingService userStreamingService) {
        emailLookupRepository.save(
                EmailLookupEntity.builder().email(event.getEmail()).username(event.getUsername())
                .build());
        userStreamingService.publishUserRegisteredEvent(event);
    }
}

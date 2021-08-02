package com.power.usercmdsvc.infrastructure.api.commands.handlers;

import com.power.usercmdsvc.domain.entities.EmailLookupEntity;
import com.power.usercmdsvc.domain.repositories.EmailLookupRepository;
import com.power.usercmdsvc.domain.service.port.output.UserStreamingService;
import com.power.usercore.domain.events.UserRegisteredEvent;
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

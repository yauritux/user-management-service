package com.power.userquerysvc.handlers;

import com.power.usercore.events.UserRegisteredEvent;
import com.power.userquerysvc.projections.DefaultUserView;
import com.power.userquerysvc.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ProcessingGroup("userEvents")
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository userRepository;

    public UserEventHandlerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventHandler
    @Override
    public void on(UserRegisteredEvent event) {
        log.debug("processing UserRegisteredEvent={}", event);
        System.out.println("Processing UserRegisteredEvent->" + event.toString());
        var userView = DefaultUserView.builder()
                .id(event.getId())
                .firstName(event.getFirstName())
                .lastName(event.getLastName())
                .username(event.getUsername())
                .email(event.getEmail()).build();
        userRepository.save(userView);
    }
}

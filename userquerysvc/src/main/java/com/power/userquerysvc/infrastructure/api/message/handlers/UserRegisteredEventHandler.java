package com.power.userquerysvc.infrastructure.api.message.handlers;

import com.google.gson.Gson;
import com.power.usercore.domain.events.UserRegisteredEvent;
import com.power.userquerysvc.domain.projections.DefaultUserView;
import com.power.userquerysvc.domain.repositories.UserRepository;
import org.springframework.context.ApplicationContext;

public class UserRegisteredEventHandler implements UserEventHandler {

    private final UserRegisteredEvent event;
    private final ApplicationContext applicationContext;

    public UserRegisteredEventHandler(String payload, ApplicationContext applicationContext) {
        this.event = new Gson().fromJson(payload, UserRegisteredEvent.class);
        this.applicationContext = applicationContext;
    }

    @Override
    public void handle() {
        var userRepository = (UserRepository) applicationContext.getBean("userRepository");
        var userView = DefaultUserView.builder()
                .id(event.getId())
                .firstName(event.getFirstName())
                .lastName(event.getLastName())
                .username(event.getUsername())
                .email(event.getEmail()).build();
        userRepository.save(userView);
    }
}

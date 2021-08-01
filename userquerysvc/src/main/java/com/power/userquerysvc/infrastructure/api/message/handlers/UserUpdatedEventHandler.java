package com.power.userquerysvc.infrastructure.api.message.handlers;

import com.google.gson.Gson;
import com.power.usercore.events.UserUpdatedEvent;
import com.power.userquerysvc.domain.repositories.UserRepository;
import org.springframework.context.ApplicationContext;

public class UserUpdatedEventHandler implements UserEventHandler {

    private final UserUpdatedEvent event;
    private final ApplicationContext applicationContext;

    public UserUpdatedEventHandler(String payload, ApplicationContext applicationContext) {
        this.event = new Gson().fromJson(payload, UserUpdatedEvent.class);
        this.applicationContext = applicationContext;
    }

    @Override
    public void handle() {
        var userRepository = (UserRepository) applicationContext.getBean("userRepository");
        var userView = userRepository.findByUsername(event.getUsername());
        userView.ifPresent(user -> {
            user.setFirstName(event.getFirstName());
            user.setLastName(event.getLastName());
            userRepository.save(user);
        });
    }
}

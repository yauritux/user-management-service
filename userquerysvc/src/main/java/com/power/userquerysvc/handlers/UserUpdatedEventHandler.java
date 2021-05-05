package com.power.userquerysvc.handlers;

import com.google.gson.Gson;
import com.power.usercore.events.UserUpdatedEvent;
import com.power.userquerysvc.repositories.UserRepository;
import com.power.userquerysvc.services.UserADService;
import org.springframework.context.ApplicationContext;

public class UserUpdatedEventHandler implements UserEventHandler {

    private final UserUpdatedEvent event;
    private final ApplicationContext applicationContext;
    private final UserADService userADService;

    public UserUpdatedEventHandler(String payload, ApplicationContext applicationContext, UserADService userADService) {
        this.event = new Gson().fromJson(payload, UserUpdatedEvent.class);
        this.applicationContext = applicationContext;
        this.userADService = userADService;
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

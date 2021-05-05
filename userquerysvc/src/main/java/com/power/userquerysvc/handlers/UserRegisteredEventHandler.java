package com.power.userquerysvc.handlers;

import com.google.gson.Gson;
import com.power.usercore.events.UserRegisteredEvent;
import com.power.userquerysvc.projections.DefaultUserView;
import com.power.userquerysvc.repositories.UserRepository;
import com.power.userquerysvc.services.UserADService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserRegisteredEventHandler implements UserEventHandler {

    private final UserRegisteredEvent event;
    private final ApplicationContext applicationContext;
    private final UserADService userADService;

    public UserRegisteredEventHandler(String payload, ApplicationContext applicationContext, UserADService userADService) {
        this.event = new Gson().fromJson(payload, UserRegisteredEvent.class);
        this.applicationContext = applicationContext;
        this.userADService = userADService;
    }

    @Override
    public void handle() {
        var userRepository = (UserRepository) applicationContext.getBean("userRepository");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var userView = DefaultUserView.builder()
                .id(event.getId())
                .firstName(event.getFirstName())
                .lastName(event.getLastName())
                .username(event.getUsername())
                .password(passwordEncoder.encode(event.getPassword()))
                .email(event.getEmail())
                .role(event.getRole())
                .build();
        System.out.println("Persisting UserView into user read database...");
        userRepository.save(userView);
        System.out.println("Adding new record into our user active directory...");
        userADService.createUser(userView);
    }
}

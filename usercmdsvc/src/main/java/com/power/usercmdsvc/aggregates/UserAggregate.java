package com.power.usercmdsvc.aggregates;

import com.power.usercmdsvc.commands.RegisterUserCommand;
import com.power.usercmdsvc.services.UserStreamingService;
import com.power.usercore.events.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Slf4j
@Aggregate
public class UserAggregate {
    @AggregateIdentifier
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;

    public UserAggregate() {}

    @CommandHandler
    public UserAggregate(RegisterUserCommand command, UserStreamingService userStreamingService) {
        var event = UserRegisteredEvent.builder()
                .id(command.getId())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .username(command.getUsername())
                .email(command.getEmail())
                .build();
        AggregateLifecycle.apply(event);
        userStreamingService.publishUserRegisteredEvent(event);
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent event) {
        log.debug("eventsourcinghandler-UserRegisteredEvent is started...");
        log.debug("firstName={}", event.getFirstName());
        this.id = event.getId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.username = event.getUsername();
        this.email = event.getEmail();
    }
}

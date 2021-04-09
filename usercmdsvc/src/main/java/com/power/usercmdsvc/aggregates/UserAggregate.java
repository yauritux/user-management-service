package com.power.usercmdsvc.aggregates;

import com.power.usercmdsvc.commands.RegisterUserCommand;
import com.power.usercore.events.UserRegisteredEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class UserAggregate {
    @AggregateIdentifier
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;

    @CommandHandler
    public UserAggregate(RegisterUserCommand command) {
        var event = UserRegisteredEvent.builder()
                .id(command.getId())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .username(command.getUsername())
                .email(command.getEmail())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent event) {
        this.id = event.getId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.username = event.getUsername();
        this.email = event.getEmail();
    }
}

package com.power.usercmdsvc.aggregates;

import com.power.usercmdsvc.commands.RegisterUserCommand;
import com.power.usercmdsvc.services.UserStreamingService;
import com.power.usercore.events.UserRegisteredEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
@NoArgsConstructor
public class UserAggregate {
    @AggregateIdentifier
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public void handle(RegisterUserCommand command, UserStreamingService userStreamingService) {
        if(this.username != null && this.username.equalsIgnoreCase(command.getUsername())) {
            throw new CommandExecutionException(String.format("User %s already exists!", command.getUsername()), null);
        }
        var event = UserRegisteredEvent.builder()
                .id(UUID.randomUUID().toString())
                .username(command.getUsername())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .build();
        AggregateLifecycle.apply(event);
        userStreamingService.publishUserRegisteredEvent(event);
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent event) {
        this.username = event.getUsername();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.email = event.getEmail();
    }
}

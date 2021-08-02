package com.power.usercmdsvc.domain.aggregates;

import com.power.usercmdsvc.domain.commands.RegisterUserCommand;
import com.power.usercmdsvc.domain.commands.UpdateUserCommand;
import com.power.usercmdsvc.domain.service.port.output.UserStreamingService;
import com.power.usercore.domain.events.UserRegisteredEvent;
import com.power.usercore.domain.events.UserUpdatedEvent;
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
    public void doHandle(RegisterUserCommand command) {
        if(this.username != null && this.username.equalsIgnoreCase(command.getUsername())) {
            throw new CommandExecutionException(
                    String.format("username %s has been taken, please choose another username!",
                            command.getUsername()), null);
        }
        var event = UserRegisteredEvent.builder()
                .id(UUID.randomUUID().toString())
                .username(command.getUsername())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateUserCommand command, UserStreamingService userStreamingService) {
        if(this.username == null) {
            throw new CommandExecutionException(
                    String.format("Cannot find username %s!", command.getUsername()), null);
        }
        if(this.firstName.equalsIgnoreCase(command.getFirstName()) &&
                this.lastName.equalsIgnoreCase(command.getLastName())) {
            throw new CommandExecutionException(
                    "There's no changes in the data, skip the update command!",
                    null
            );
        }
        var event = UserUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .username(command.getUsername())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .build();
        AggregateLifecycle.apply(event);
        userStreamingService.publishUserUpdatedEvent(event);
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent event) {
        this.username = event.getUsername();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.email = event.getEmail();
    }
}

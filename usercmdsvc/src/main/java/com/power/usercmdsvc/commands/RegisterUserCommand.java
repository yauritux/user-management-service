package com.power.usercmdsvc.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserCommand {
    @TargetAggregateIdentifier
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}

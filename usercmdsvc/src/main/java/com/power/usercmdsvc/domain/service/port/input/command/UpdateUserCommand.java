package com.power.usercmdsvc.domain.service.port.input.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserCommand {
    @TargetAggregateIdentifier
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}

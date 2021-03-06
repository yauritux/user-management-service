package com.power.usercmdsvc.infrastructure.adapter.input;

import com.power.usercmdsvc.domain.service.port.input.UserCommandService;
import com.power.usercmdsvc.domain.service.port.input.command.RegisterUserCommand;
import com.power.usercmdsvc.infrastructure.api.controller.dto.request.RegisterUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserCommandServiceImpl implements UserCommandService<RegisterUserCommand, RegisterUserRequest> {

    private final CommandGateway commandGateway;

    public UserCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public Mono<RegisterUserCommand> registerUser(RegisterUserRequest request) {
        var command = RegisterUserCommand.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        try {
            commandGateway.send(command);
            return Mono.just(command);
        } catch (Exception e) {
            log.error(String.format("Failed to send register user command.Error= %s", e.getMessage()));
            return Mono.error(e);
        }
    }
}

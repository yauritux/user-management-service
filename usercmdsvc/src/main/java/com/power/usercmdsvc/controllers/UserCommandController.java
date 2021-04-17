package com.power.usercmdsvc.controllers;

import com.power.usercmdsvc.commands.RegisterUserCommand;
import com.power.usercmdsvc.dto.RegisterUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserCommandController {

    private final CommandGateway commandGateway;

    public UserCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<RegisterUserResponse>> registerUser(@RequestBody RegisterUserCommand command) {
        return commandGateway.send(command).thenApply(it -> {
            return new ResponseEntity<>(
                    new RegisterUserResponse(
                            command.getUsername(), "User successfully registered"), HttpStatus.CREATED
            );
        }).exceptionally(e -> {
            var message = "Failed to register user. Error=" + e.getLocalizedMessage();
            return new ResponseEntity<>(new RegisterUserResponse(command.getUsername(), message), HttpStatus.INTERNAL_SERVER_ERROR);
        });
    }
}

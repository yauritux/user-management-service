package com.power.usercmdsvc.controllers;

import com.power.usercmdsvc.commands.RegisterUserCommand;
import com.power.usercmdsvc.commands.UpdateUserCommand;
import com.power.usercmdsvc.dto.RegisterUserResponse;
import com.power.usercmdsvc.dto.UpdateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{username}")
    public CompletableFuture<ResponseEntity<UpdateUserResponse>> updateUser(
            @PathVariable String username, @RequestBody UpdateUserCommand command) {
        command.setUsername(username);
        return commandGateway.send(command).thenApply(it -> {
            return new ResponseEntity<>(
                    new UpdateUserResponse(
                            command.getUsername(), "User successfully updated"), HttpStatus.OK
            );
        }).exceptionally(e -> {
            var message = "Cannot update user. Details=" + e.getLocalizedMessage();
            return new ResponseEntity<>(new UpdateUserResponse(command.getUsername(), message), HttpStatus.OK);
        });
    }
}

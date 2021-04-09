package com.power.usercmdsvc.controllers;

import com.power.usercmdsvc.commands.RegisterUserCommand;
import com.power.usercmdsvc.dto.RegisterUserResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserCommandController {

    private final CommandGateway commandGateway;

    public UserCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandGateway.send(command);
            return new ResponseEntity<>(new RegisterUserResponse(id, "Successfully registered new user"), HttpStatus.CREATED);
        } catch (Exception e) {
            var message = "Failed to register user. Error=" + e.getMessage();
            return new ResponseEntity<>(new RegisterUserResponse(id, message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

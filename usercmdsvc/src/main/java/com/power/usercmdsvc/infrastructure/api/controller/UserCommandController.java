package com.power.usercmdsvc.infrastructure.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.power.usercmdsvc.infrastructure.api.controllers.dto.requests.RegisterUserRequest;
import com.power.usercmdsvc.domain.service.port.inputs.UserCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserCommandController {

    private final UserCommandService commandService;

    public UserCommandController(UserCommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public Mono<?> registerUser(@RequestBody RegisterUserRequest request) {
        return commandService.registerUser(request)
                .map(m -> ResponseEntity.status(HttpStatus.CREATED).body(m))
                .onErrorResume(JsonProcessingException.class,
                        (e) -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Mono.just(e.toString()), String.class));
    }

//    @PutMapping("/{username}")
//    public CompletableFuture<ResponseEntity<UpdateUserResponse>> updateUser(
//            @PathVariable String username, @RequestBody UpdateUserCommand command) {
//        command.setUsername(username);
//        return commandGateway.send(command).thenApply(it -> {
//            return new ResponseEntity<>(
//                    new UpdateUserResponse(
//                            command.getUsername(), "User successfully updated"), HttpStatus.OK
//            );
//        }).exceptionally(e -> {
//            var message = "Cannot update user. Details=" + e.getLocalizedMessage();
//            return new ResponseEntity<>(new UpdateUserResponse(command.getUsername(), message), HttpStatus.OK);
//        });
//    }
}

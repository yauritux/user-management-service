package com.power.usercmdsvc.port.input.service;

import com.power.usercmdsvc.port.input.dto.RegisterUserRequest;
import reactor.core.publisher.Mono;

public interface UserCommandService<T> {

    Mono<T> registerUser(RegisterUserRequest request);
}

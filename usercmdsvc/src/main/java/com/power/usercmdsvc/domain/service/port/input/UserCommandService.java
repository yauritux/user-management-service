package com.power.usercmdsvc.domain.service.port.input;

import reactor.core.publisher.Mono;

public interface UserCommandService<T, R> {

    Mono<T> registerUser(R request);
}

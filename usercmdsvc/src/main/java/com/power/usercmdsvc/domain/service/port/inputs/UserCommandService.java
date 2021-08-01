package com.power.usercmdsvc.domain.service.port.inputs;

import reactor.core.publisher.Mono;

public interface UserCommandService<T, R> {

    Mono<T> registerUser(R request);
}

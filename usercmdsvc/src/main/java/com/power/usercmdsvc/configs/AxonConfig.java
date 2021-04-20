package com.power.usercmdsvc.configs;

import com.power.usercmdsvc.commands.interceptors.UserRegistrationDispatchInterceptor;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public CommandGateway commandGateway(CommandBus commandBus, UserRegistrationDispatchInterceptor interceptor) {
        return DefaultCommandGateway.builder()
                .commandBus(commandBus)
                .dispatchInterceptors(interceptor)
                .build();
    }
}

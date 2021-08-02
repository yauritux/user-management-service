package com.power.usercmdsvc.infrastructure.api.commands.interceptors;

import com.power.usercmdsvc.domain.commands.RegisterUserCommand;
import com.power.usercmdsvc.domain.repositories.EmailLookupRepository;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Intercepts the user registration command message and throws IllegalStateException when already account aggregate with
 * email address already exists. Links to "Validation through a Dispatch Interceptor' section in in this [set based
 * validation blog](https://axoniq.io/blog-overview/set-based-validation)
 *
 * @author Yauri Attamimi
 */
@Component
public class UserRegistrationDispatchInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final EmailLookupRepository emailLookupRepository;

    public UserRegistrationDispatchInterceptor(EmailLookupRepository emailLookupRepository) {
        this.emailLookupRepository = emailLookupRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (i, m) -> {
            if(RegisterUserCommand.class.equals(m.getPayloadType())) {
                final RegisterUserCommand registerUserCommand = (RegisterUserCommand) m.getPayload();

                if(emailLookupRepository.existsById(registerUserCommand.getEmail())) {
                    throw new CommandExecutionException(String.format("Account with email address %s already exists!",
                            registerUserCommand.getEmail()), null);
                }
            }
            return m;
        };
    }
}

package com.power.userquerysvc.infrastructure.config;

import com.google.gson.Gson;
import com.power.usercore.events.BaseEvent;
import com.power.userquerysvc.infrastructure.handlers.UserEventHandler;
import com.rabbitmq.client.Channel;
import org.axonframework.extensions.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("rabbitMQSpringAMQPMessageSource")
public class RabbitMQSpringAMQPMessageSource extends SpringAMQPMessageSource implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    public RabbitMQSpringAMQPMessageSource(final AMQPMessageConverter messageConverter) {
        super(messageConverter);
    }

    @RabbitListener(queues = {"userEvents", "userUpdatedEvents"})
    @Override
    public void onMessage(final Message message, final Channel channel) {
        String messageBody = new String(message.getBody());
        super.onMessage(message, channel);
        Gson g = new Gson();
        try {
            var payload = g.fromJson(messageBody, BaseEvent.class);
            var eventHandler = (UserEventHandler) Class.forName(
                    UserEventHandler.EVENT_HANDLER_PACKAGE + "." + payload.getEventHandler()
            ).getConstructor(String.class, ApplicationContext.class).newInstance(messageBody, applicationContext);
            eventHandler.handle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

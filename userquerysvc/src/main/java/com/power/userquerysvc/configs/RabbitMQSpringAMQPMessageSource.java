package com.power.userquerysvc.configs;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.extensions.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rabbitMQSpringAMQPMessageSource")
@Slf4j
public class RabbitMQSpringAMQPMessageSource extends SpringAMQPMessageSource {

    @Autowired
    public RabbitMQSpringAMQPMessageSource(final AMQPMessageConverter messageConverter) {
        super(messageConverter);
    }

    @RabbitListener(queues = "userEvents.topic.userEvents")
    @Override
    public void onMessage(final Message message, final Channel channel) {
        log.debug("received message: message={}, channel={}", message, channel);
        System.out.println("Received message = " + message.toString());
        super.onMessage(message, channel);
    }
}

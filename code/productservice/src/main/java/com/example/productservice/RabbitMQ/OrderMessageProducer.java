package com.example.productservice.RabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrderMessage(String message){
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, "routing.key", message);
    }
}

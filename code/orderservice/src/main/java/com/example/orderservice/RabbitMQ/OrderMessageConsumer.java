package com.example.orderservice.RabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageConsumer {

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_NAME)
    public void listen(String message) {
        // TODO: implement order processing logic from here
        System.out.println("Message received: <" + message + ">");
    }
}

package com.zikozee.rabbitmq_producer.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author : zikoz
 * @created : 04 Jun, 2021
 */

//@Service
@RequiredArgsConstructor
public class HelloRabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendHello(String name){
        rabbitTemplate.convertAndSend("zikozee.hello", "hello " + name);
    }
}

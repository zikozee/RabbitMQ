package com.zikozee.rabbitmq_consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author : zikoz
 * @created : 04 Jun, 2021
 */

//@Service
@Slf4j
public class HelloRabbitConsumer {

//    @RabbitListener(queues = "zikozee.hello")
//    public void listen(String message){
//        log.info("Consuming {}", message);
//    }
}

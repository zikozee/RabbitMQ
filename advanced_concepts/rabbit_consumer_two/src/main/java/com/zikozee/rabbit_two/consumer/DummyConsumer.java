package com.zikozee.rabbit_two.consumer;

import com.zikozee.rabbit_two.entity.DummyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author : zikoz
 * @created : 07 Jun, 2021
 */

@Slf4j
//@Service
public class DummyConsumer {
    public static final String QUEUE ="q.dummy";

    @RabbitListener(queues = QUEUE)
    public void listenDummy(DummyMessage message){
        // todo info: no need to convert to Object, Jackson2JsonMessageConverter will handle it
        // todo info: however, ensure package name is same
        log.info("Message is {}", message);

    }
}

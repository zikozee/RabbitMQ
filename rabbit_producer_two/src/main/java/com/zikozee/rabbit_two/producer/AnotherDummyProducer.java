package com.zikozee.rabbit_two.producer;

import com.zikozee.rabbit_two.entity.DummyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : zikoz
 * @created : 07 Jun, 2021
 */

@Service
@RequiredArgsConstructor
public class AnotherDummyProducer {
    public static final String EXCHANGE = "x.another-dummy";

    private final RabbitTemplate rabbitTemplate;

    public void sendDummy(DummyMessage message){
        rabbitTemplate.convertAndSend(EXCHANGE, "", message);
    }
}

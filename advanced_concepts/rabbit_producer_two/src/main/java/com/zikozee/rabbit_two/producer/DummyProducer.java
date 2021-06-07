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
public class DummyProducer {
    public static final String EXCHANGE = "x.dummy";

    private final RabbitTemplate rabbitTemplate;

    public void sendDummy(DummyMessage message){
        // todo info: no need to convert to json string, Jackson2JsonMessageConverter will handle it
        // todo info: however, ensure package name is same
        rabbitTemplate.convertAndSend(EXCHANGE, "", message);
    }
}

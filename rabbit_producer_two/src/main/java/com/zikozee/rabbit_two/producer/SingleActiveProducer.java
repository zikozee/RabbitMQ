package com.zikozee.rabbit_two.producer;

import com.zikozee.rabbit_two.entity.DummyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : zikoz
 * @created : 20 Jun, 2021
 */

@Service
@RequiredArgsConstructor
public class SingleActiveProducer {
    public static final String EXCHANGE = "x.single";

    private final RabbitTemplate rabbitTemplate;

    public void sendDummy(){
        for (var i = 0; i < 10_000; i++) {
            var message = new DummyMessage("Message "+ i, i);
            rabbitTemplate.convertAndSend(EXCHANGE, "", message);
        }
    }
}

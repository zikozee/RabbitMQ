package com.zikozee.rabbit_two.producer;

import com.zikozee.rabbit_two.entity.DummyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author : zikoz
 * @created : 20 Jun, 2021
 */

@Service
@RequiredArgsConstructor
public class MultiplePrefetchProducer {

    public static final String EXCHANGE = "x.transaction";
    public static final String EXCHANGE2 = "x.scheduler";
    private final RabbitTemplate rabbitTemplate;

    public void simulateTransaction(){
        for (var i = 0; i < 20_000; i++) {
                var message = new DummyMessage("Transaction " + LocalTime.now(), i);
                rabbitTemplate.convertAndSend(EXCHANGE, "", message);
        }
    }

    public void simulateScheduler(){
        for (var i = 0; i < 20_000; i++) {
            var message = new DummyMessage("Scheduler " + LocalTime.now(), i);
            rabbitTemplate.convertAndSend(EXCHANGE2, "", message);
        }
    }
}

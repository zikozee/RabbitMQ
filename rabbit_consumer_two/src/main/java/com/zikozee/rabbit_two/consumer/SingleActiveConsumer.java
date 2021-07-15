package com.zikozee.rabbit_two.consumer;

import com.zikozee.rabbit_two.entity.DummyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author : zikoz
 * @created : 20 Jun, 2021
 */

@Slf4j
//@Service
public class SingleActiveConsumer {

    public static final String QUEUE = "q.single";

    @RabbitListener(queues = QUEUE, concurrency = "5")
    public void listenDummy(DummyMessage message) throws InterruptedException {
        log.info("Consuming {}", message);
        TimeUnit.SECONDS.sleep(1);
    }
}

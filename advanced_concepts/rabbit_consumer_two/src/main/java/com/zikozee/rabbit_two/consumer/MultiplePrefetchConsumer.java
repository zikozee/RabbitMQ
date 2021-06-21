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
public class MultiplePrefetchConsumer {

    public static final String TRANSACTION_QUEUE = "q.transaction";
    public static final String SCHEDULER_QUEUE = "q.scheduler";

    @RabbitListener(queues = TRANSACTION_QUEUE, concurrency = "2")
    public void listenTransaction(DummyMessage message) throws InterruptedException {
        log.info("Taking transaction {}", message);
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @RabbitListener(queues = SCHEDULER_QUEUE, concurrency = "2", containerFactory = "prefetchContainerFactory")
    public void listenScheduler(DummyMessage message) throws InterruptedException {
        log.info("Taking scheduler {}", message);
        TimeUnit.MINUTES.sleep(1);
    }
}

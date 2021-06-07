package com.zikozee.rabbitmq_consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author : zikoz
 * @created : 04 Jun, 2021
 */

@Slf4j
//@Service
public class FixedRateConsumer {
    static final String QUEUE_NAME = "course.fixedrate";
//    static final String CONCURRENCY_NUMBER = "3";
    static final String MIN_CONCURRENCY = "3";
    static final String MAX_CONCURRENCY = "7";
    static final String SEPARATOR = "-";

    // 3 stands for 3 consumers for fixed rate producer>> use total number based on your hardware
   // @RabbitListener(queues = QUEUE_NAME, concurrency = CONCURRENCY_NUMBER) // 3
    //we can also set minimum and maximum value and let spring manage it for us
    @RabbitListener(queues = QUEUE_NAME, concurrency = MIN_CONCURRENCY + SEPARATOR + MAX_CONCURRENCY) // "3-7"     SPRING WILL MANAGE THE THREAD
    private void listen(String message) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(1000, 2000));
        log.info("{} : Consuming {}",Thread.currentThread().getName(), message);
    }
}

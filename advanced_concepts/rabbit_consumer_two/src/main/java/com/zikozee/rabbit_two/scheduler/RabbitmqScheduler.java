package com.zikozee.rabbit_two.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author : zikoz
 * @created : 07 Jun, 2021
 */
@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class RabbitmqScheduler {

    private final RabbitListenerEndpointRegistry registry;

    @Scheduled(cron = "0 0 23 * * *") //  stop all listeners at 11 pm
    public void stopAllListeners(){
        registry.getListenerContainers().forEach(c -> {
            log.info("Stopping listener container {}", c);
            c.stop();
        });
    }

    @Scheduled(cron = "1 0 0 * * *")   //  starts all listeners at 00:00:01am
    public void startAllListeners(){
        registry.getListenerContainers().forEach(c -> {
            log.info("Stopping listener container {}", c);
            c.start();
        });
    }
}

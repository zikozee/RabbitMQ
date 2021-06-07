package com.zikozee.rabbitmq_producer.scheduler;

import com.zikozee.rabbitmq_producer.client.RabbitmqClient;
import com.zikozee.rabbitmq_producer.entity.RabbitmqQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author : zikoz
 * @created : 06 Jun, 2021
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitmqScheduler {

    private final RabbitmqClient client;

    @Scheduled(fixedDelay = 90000)
    public void sweepDirtyQueues(){
        try {
            var dirtyQueues = client.getAllQueues().stream()
                    .filter(RabbitmqQueue::isDirty).collect(Collectors.toList());

            dirtyQueues.forEach(q -> log.info("Queue {} has {} unprocessed messages", q.getName(), q.getMessages()));
        } catch (Exception e){
            log.warn("Cannot sweep queue : {}", e.getMessage());
        }
    }
}

package com.zikozee.rabbitmq_producer.retry.fanout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zikozee.rabbitmq_producer.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : zikoz
 * @created : 04 Jun, 2021
 */

@Service
@RequiredArgsConstructor
public class RetryEmployeeProducer {
    public static final String EXCHANGE_NAME = "x.guideline2.work";
    public static final String ROUTING_KEY = ""; //since fanout will send to all queues, hence no effect
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage(Employee data) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(data);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, json);
    }
}

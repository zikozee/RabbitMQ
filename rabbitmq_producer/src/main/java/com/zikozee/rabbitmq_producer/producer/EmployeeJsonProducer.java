package com.zikozee.rabbitmq_producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zikozee.rabbitmq_producer.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author : zikoz
 * @created : 04 Jun, 2021
 */

//@Service
@RequiredArgsConstructor
public class EmployeeJsonProducer {
    public static final String QUEUE = "course.employee";
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage(Employee data) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(data);
        rabbitTemplate.convertAndSend(QUEUE, json);
    }
}

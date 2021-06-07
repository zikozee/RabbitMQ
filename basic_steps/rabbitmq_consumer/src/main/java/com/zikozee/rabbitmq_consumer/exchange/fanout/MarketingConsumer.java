package com.zikozee.rabbitmq_consumer.exchange.fanout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zikozee.rabbitmq_consumer.entity.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author : zikoz
 * @created : 04 Jun, 2021
 */

@Slf4j
//@Service
@RequiredArgsConstructor
public class MarketingConsumer {
    public static final String QUEUE = "q.hr.marketing";
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = QUEUE)
    private void listener(String message) throws JsonProcessingException {
        var employee = objectMapper.readValue(message, Employee.class);

        log.info("Employee in marketing is {}", employee);
    }
}

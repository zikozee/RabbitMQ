package com.zikozee.rabbitmq_consumer.retry.spring_retry.fanout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zikozee.rabbitmq_consumer.entity.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author : zikoz
 * @created : 07 Jun, 2021
 */

@Slf4j
//@Service
@RequiredArgsConstructor
public class SpringEmployeeConsumer {
    public static final String QUEUE_ACCT = "q.spring2.accounting.work";
    public static final String QUEUE_MAR = "q.spring2.marketing.work";

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = QUEUE_ACCT)
    public void listenAccounting(String message) throws JsonProcessingException {
        var employee = objectMapper.readValue(message, Employee.class);
        log.info("On accounting, consuming {}", employee);

        if (ObjectUtils.isEmpty(employee.getName())) throw new IllegalArgumentException("name is empty");

        log.info("On accounting, employee is {}", employee);
    }

    @RabbitListener(queues = QUEUE_MAR)
    public void listenMarketing(String message) throws JsonProcessingException {
        var employee = objectMapper.readValue(message, Employee.class);
        log.info("On accounting, consuming {}", employee);
        log.info("On accounting, employee is {}", employee);
    }



}

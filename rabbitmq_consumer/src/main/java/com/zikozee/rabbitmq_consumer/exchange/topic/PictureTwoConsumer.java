package com.zikozee.rabbitmq_consumer.exchange.topic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zikozee.rabbitmq_consumer.entity.Picture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author : zikoz
 * @created : 04 Jun, 2021
 */

@Slf4j
//@Service
@RequiredArgsConstructor
public class PictureTwoConsumer {
    //todo info:  instead of creating for classes just for this demo, let use an array
    public static final String PNG_JPG_QUEUE = "q.picture.image";
    public static final String SVG_QUEUE = "q.picture.vector";
    public static final String SVG_LOGGING_QUEUE = "q.picture.log";
    public static final String SVG_SIZE_QUEUE = "q.picture.filter";

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = {PNG_JPG_QUEUE, SVG_QUEUE, SVG_LOGGING_QUEUE, SVG_SIZE_QUEUE})
    public void listen(Message messageAmqp) throws JsonProcessingException {
        var jsonString = new String(messageAmqp.getBody());
        var picture = objectMapper.readValue(jsonString, Picture.class);

        log.info("Consuming picture : {} with routing key : {}", picture, messageAmqp.getMessageProperties().getReceivedRoutingKey());
    }
}

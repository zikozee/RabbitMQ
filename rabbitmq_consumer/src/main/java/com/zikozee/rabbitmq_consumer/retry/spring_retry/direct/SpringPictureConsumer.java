package com.zikozee.rabbitmq_consumer.retry.spring_retry.direct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zikozee.rabbitmq_consumer.entity.Picture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author : zikoz
 * @created : 07 Jun, 2021
 */

@Slf4j
//@Service
@RequiredArgsConstructor
public class SpringPictureConsumer {
    public static final String QUEUE_IMAGE = "q.spring.image.work";
    public static final String QUEUE_VECTOR = "q.spring.vector.work";

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = QUEUE_IMAGE)
    public void listenImage(String message) throws JsonProcessingException {
        var picture = objectMapper.readValue(message, Picture.class);
        log.info("Consuming image {}", picture.getName());

        if (picture.getSize() > 9000){
            throw new IllegalArgumentException("Image too large : " + picture.getName());
        }

        //business logic
        log.info("processing image : " + picture.getName());

    }

    @RabbitListener(queues = QUEUE_VECTOR)
    public void listenVector(String message) throws JsonProcessingException {
        var picture = objectMapper.readValue(message, Picture.class);
        log.info("Consuming vector {}", picture.getName());

        //business logic
        log.info("processing vector : " + picture.getName());
    }
}

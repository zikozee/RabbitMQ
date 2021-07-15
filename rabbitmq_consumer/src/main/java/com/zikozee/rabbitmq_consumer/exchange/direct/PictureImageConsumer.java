package com.zikozee.rabbitmq_consumer.exchange.direct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zikozee.rabbitmq_consumer.entity.Picture;
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
public class PictureImageConsumer {
    public static final String QUEUE = "q.picture.image";

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = QUEUE)
    public void listen(String message) throws JsonProcessingException {
        var picture = objectMapper.readValue(message, Picture.class);

        log.info("On Image : {}", picture);
    }
}

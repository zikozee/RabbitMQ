package com.zikozee.rabbitmq_producer.exchange.direct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zikozee.rabbitmq_producer.entity.Picture;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : zikoz
 * @created : 04 Jun, 2021
 */

@Service
@RequiredArgsConstructor
public class PictureProducer {
    public static final String EXCHANGE_NAME = "x.picture";

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    //todo info: picture type as routing_key
    public void sendMessage(Picture picture) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(picture);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, picture.getType(), json);
    }
}

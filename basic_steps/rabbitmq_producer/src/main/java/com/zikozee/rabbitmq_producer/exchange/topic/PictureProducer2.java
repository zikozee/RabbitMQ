package com.zikozee.rabbitmq_producer.exchange.topic;

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
public class PictureProducer2 {
    public static final String EXCHANGE_NAME = "x.picture2";

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    //todo info: picture type as routing_key
    public void sendMessage(Picture picture) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(picture);

        var sb = new StringBuilder();

        //1st word is picture source
        sb.append(picture.getSource());
        sb.append(".");

        // 2nd word is based on picture size
        if(picture.getSize() > 400) sb.append("large");
        else sb.append("small");
        sb.append(".");

        // 3rd word is picture type
        sb.append(picture.getType());

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, sb.toString() , json);
    }
}

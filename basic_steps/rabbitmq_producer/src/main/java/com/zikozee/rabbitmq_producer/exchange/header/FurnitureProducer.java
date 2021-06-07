package com.zikozee.rabbitmq_producer.exchange.header;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zikozee.rabbitmq_producer.entity.Furniture;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : zikoz
 * @created : 05 Jun, 2021
 */

@Service
@RequiredArgsConstructor
public class FurnitureProducer {
    public static final String EXCHANGE_NAME = "x.promotion";

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage(Furniture furniture) throws JsonProcessingException {
        var messageProperties = new MessageProperties();
        messageProperties.setHeader("color", furniture.getColor());
        messageProperties.setHeader("material", furniture.getMaterial());

        var json = objectMapper.writeValueAsString(furniture);

        var message = new Message(json.getBytes(), messageProperties);

        rabbitTemplate.send(EXCHANGE_NAME, "", message);
    }
}

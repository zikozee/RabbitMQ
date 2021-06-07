package com.zikozee.rabbitmq_consumer.customException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.zikozee.rabbitmq_consumer.entity.Picture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author : zikoz
 * @created : 04 Jun, 2021
 */

@Slf4j
//@Service
@RequiredArgsConstructor
public class MyPictureImageConsumer {
    public static final String QUEUE = "q.mypicture.image";

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = QUEUE)
    public void listenAutomaticRejection(String message) throws JsonProcessingException {
        var picture = objectMapper.readValue(message, Picture.class);
        if(picture.getSize() > 9000) throw new AmqpRejectAndDontRequeueException("Picture size too large : "  + picture);
        log.info("On Image : {}", picture);
    }

    //MANUAL REJECTION
//    @RabbitListener(queues = QUEUE)
//    public void listenManualRejection(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
//        var picture = objectMapper.readValue(message, Picture.class);
//        if(picture.getSize() > 9000){
//            log.info("SIZE: {}", picture.getSize());
//            channel.basicReject(tag, false);
//            return;
//        }
//        log.info("On Image : {}", picture);
//        channel.basicAck(tag, false);
//    }
}

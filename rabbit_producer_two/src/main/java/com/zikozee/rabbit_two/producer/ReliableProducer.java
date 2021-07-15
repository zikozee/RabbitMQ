package com.zikozee.rabbit_two.producer;

import com.zikozee.rabbit_two.entity.DummyMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author : zikoz
 * @created : 20 Jun, 2021
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ReliableProducer {
    private final RabbitTemplate rabbitTemplate;

    @PostConstruct
    private void registerCallback(){
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> {
            if(correlationData == null) return;

            if(ack) log.info("Message with correlation {} published", correlationData.getId());
            else log.warn("Invalid exchange for message with correlation {} published", correlationData.getId());
        }));

        rabbitTemplate.setReturnsCallback(returned -> {
            log.info("return callback");

            if (returned.getReplyText() != null && returned.getReplyText().equalsIgnoreCase("NO_ROUTE")){
                var id = returned.getMessage().getMessageProperties()
                        .getHeader("spring_returned_message_correlation").toString();
                log.warn("Invalid routing key for message {}", id);
            }
        });
    }

    public void sendDummyWIthInvalidRoutingKey(DummyMessage message){
        var correlationData = new CorrelationData(Integer.toString(message.getPublishOrder()));
        rabbitTemplate.convertAndSend("x.dummy2", "invalid-routing-key", message, correlationData);
    }

    public void sendDummyWIthInvalidExchange(DummyMessage message){
        var correlationData = new CorrelationData(Integer.toString(message.getPublishOrder()));
        rabbitTemplate.convertAndSend("x.non-exists-exchange", "", message, correlationData);
    }
}

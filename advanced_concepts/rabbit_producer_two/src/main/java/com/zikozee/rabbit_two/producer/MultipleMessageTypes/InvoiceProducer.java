package com.zikozee.rabbit_two.producer.MultipleMessageTypes;

/**
 * @author : zikoz
 * @created : 20 Jun, 2021
 */

import com.zikozee.rabbit_two.entity.InvoiceCancelledMessage;
import com.zikozee.rabbit_two.entity.InvoiceCreatedMessage;
import com.zikozee.rabbit_two.entity.InvoicePaidMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * One Queue  + Multiple Message Types
 */
@Service
@RequiredArgsConstructor
public class InvoiceProducer {
    public static final String EXCHANGE = "x.invoice";

    private final RabbitTemplate rabbitTemplate;

    //todo info  routing key was  "" >>> until now when doing consistent hash

    public void sendInvoiceCreated(InvoiceCreatedMessage message){
        rabbitTemplate.convertAndSend(EXCHANGE, message.getInvoiceNumber(), message);
    }

    public void sendInvoicePaid(InvoicePaidMessage message){
        rabbitTemplate.convertAndSend(EXCHANGE, message.getInvoiceNumber(), message);
    }

    public void sendInvoiceCancelled(InvoiceCancelledMessage message){
        rabbitTemplate.convertAndSend(EXCHANGE, message.getInvoiceNumber(), message);
    }
}

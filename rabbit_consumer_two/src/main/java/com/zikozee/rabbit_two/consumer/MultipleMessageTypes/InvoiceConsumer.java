package com.zikozee.rabbit_two.consumer.MultipleMessageTypes;

/**
 * @author : zikoz
 * @created : 20 Jun, 2021
 */

import com.zikozee.rabbit_two.entity.InvoiceCancelledMessage;
import com.zikozee.rabbit_two.entity.InvoiceCreatedMessage;
import com.zikozee.rabbit_two.entity.InvoicePaidMessage;
import com.zikozee.rabbit_two.entity.PaymentCancelStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import static com.zikozee.rabbit_two.consumer.MultipleMessageTypes.InvoiceConsumer.QUEUE;

/**
 * One Queue  + Multiple Message Types
 */

@Slf4j
//@Service
@RabbitListener(queues = QUEUE)
public class InvoiceConsumer {
    public static final String QUEUE = "q.invoice";

    @RabbitHandler
    public void handleInvoiceCreated(InvoiceCreatedMessage message){
        log.info("Invoice created: {}", message);
    }

    @RabbitHandler
    public void handleInvoicePaid(InvoicePaidMessage message){
        log.info("Invoice paid: {}", message);
    }

    @RabbitHandler(isDefault = true)
    public void handleDefault(Object message){
        log.warn("Handling default: {}", message);
    }

    @RabbitHandler
    @SendTo("x.invoice.cancel/")
    public PaymentCancelStatus handleInvoiceCancelled(InvoiceCancelledMessage message){
        var randomStatus = ThreadLocalRandom.current().nextBoolean();

        return new PaymentCancelStatus(randomStatus, LocalDate.now(), message.getInvoiceNumber());
    }
}

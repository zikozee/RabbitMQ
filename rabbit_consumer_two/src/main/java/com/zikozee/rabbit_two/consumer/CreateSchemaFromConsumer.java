package com.zikozee.rabbit_two.consumer;

import com.zikozee.rabbit_two.entity.DummyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author : zikoz
 * @created : 07 Jun, 2021
 */

@Slf4j
@Service
public class CreateSchemaFromConsumer {
    public static final String EXCHANGE = "x.auto-dummy";
    public static final String QUEUE = "q.auto-dummy";

    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = QUEUE, durable = "true"),
            exchange = @Exchange(name = EXCHANGE, type = ExchangeTypes.DIRECT, durable = "true"),
            key = "routing-key",
            ignoreDeclarationExceptions = "true")
    )
    public void listenDummy(DummyMessage message) {

        log.info("Message is {}", message);

    }
}

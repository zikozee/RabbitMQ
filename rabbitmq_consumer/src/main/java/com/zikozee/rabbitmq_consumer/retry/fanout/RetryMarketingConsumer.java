package com.zikozee.rabbitmq_consumer.retry.fanout;

import java.io.IOException;

import com.zikozee.rabbitmq_consumer.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

@Slf4j
//@Service
public class RetryMarketingConsumer {

	@Autowired
	private ObjectMapper objectMapper;

	@RabbitListener(queues = "q.guideline2.marketing.work")
	public void listen(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
			throws IOException {
		var employee = objectMapper.readValue(message.getBody(), Employee.class);
		log.info("On marketing : {}", employee);
		channel.basicAck(tag, false);
	}
}
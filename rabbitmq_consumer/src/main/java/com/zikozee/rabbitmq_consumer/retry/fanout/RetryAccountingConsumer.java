package com.zikozee.rabbitmq_consumer.retry.fanout;

import java.io.IOException;

import com.zikozee.rabbitmq_consumer.entity.Employee;
import com.zikozee.rabbitmq_consumer.retry.dlx_err_handler.DlxProcessingErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class RetryAccountingConsumer {

	private static final String DEAD_EXCHANGE_NAME = "x.guideline2.dead";

	private DlxProcessingErrorHandler dlxProcessingErrorHandler;

	@Autowired
	private ObjectMapper objectMapper;

	public RetryAccountingConsumer() {
		this.dlxProcessingErrorHandler = new DlxProcessingErrorHandler(DEAD_EXCHANGE_NAME);
	}

	@RabbitListener(queues = "q.guideline2.accounting.work")
	public void listen(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
			throws IOException {
		try {
			var employee = objectMapper.readValue(message.getBody(), Employee.class);

			if (StringUtils.isEmpty(employee.getName())) {
				throw new IllegalArgumentException("Name is empty");
			} else {
				log.info("On accounting : {}", employee);
				channel.basicAck(tag, false);
			}
		} catch (Exception e) {
			log.warn("Error processing message : {} : {}", new String(message.getBody()), e.getMessage());
			dlxProcessingErrorHandler.handleErrorProcessingMessage(message, channel, tag);
		}

	}
}
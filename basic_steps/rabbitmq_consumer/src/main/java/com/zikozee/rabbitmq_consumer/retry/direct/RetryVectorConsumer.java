package com.zikozee.rabbitmq_consumer.retry.direct;

import java.io.IOException;

import com.zikozee.rabbitmq_consumer.retry.dlx_err_handler.DlxProcessingErrorHandler;
import com.zikozee.rabbitmq_consumer.entity.Picture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
public class RetryVectorConsumer {

	private static final String DEAD_EXCHANGE_NAME = "x.guideline.dead";

	private DlxProcessingErrorHandler dlxProcessingErrorHandler;

	@Autowired
	private ObjectMapper objectMapper;

	public RetryVectorConsumer() {
		this.dlxProcessingErrorHandler = new DlxProcessingErrorHandler(DEAD_EXCHANGE_NAME);
	}

	@RabbitListener(queues = "q.guideline.vector.work")
	public void listen(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag)
			throws InterruptedException, JsonParseException, JsonMappingException, IOException {
		try {
			var p = objectMapper.readValue(message.getBody(), Picture.class);
			// process the image
			if (p.getSize() > 9000) {
				// throw exception, we will use DLX handler for retry mechanism
				throw new IOException("Size too large");
			} else {
				log.info("Convert to image, creating thumbnail, & publishing : {}", p);
				// you must acknowledge that message already processed
				channel.basicAck(deliveryTag, false);
			}
		} catch (IOException e) {
			log.warn("Error processing message : {} : {}", new String(message.getBody()), e.getMessage());
			dlxProcessingErrorHandler.handleErrorProcessingMessage(message, channel, deliveryTag);
		}
	}

}
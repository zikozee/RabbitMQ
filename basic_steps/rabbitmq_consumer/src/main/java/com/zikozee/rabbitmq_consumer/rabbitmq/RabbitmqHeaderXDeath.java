package com.zikozee.rabbitmq_consumer.rabbitmq;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Represents RabbitMQ Header, part x-death. Tested on RabbitMQ 3.8.x.
 * 
 * @author zikozee
 */
@Getter @Setter @EqualsAndHashCode
public class RabbitmqHeaderXDeath {

	private int count;
	private String exchange;
	private String queue;
	private String reason;
	private List<String> routingKeys;
	private Date time;

}

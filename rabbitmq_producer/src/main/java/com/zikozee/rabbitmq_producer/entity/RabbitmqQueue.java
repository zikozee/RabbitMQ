package com.zikozee.rabbitmq_producer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : zikoz
 * @created : 06 Jun, 2021
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
@ToString
public class RabbitmqQueue {

    private long messages;
    private String name;

    public boolean isDirty(){
        return messages > 0;
    }
}

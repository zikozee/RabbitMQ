package com.zikozee.rabbitmq_consumer.entity;

import lombok.*;

/**
 * @author : zikoz
 * @created : 04 Jun, 2021
 */

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Picture {

    private String name;
    private String type;
    private String source;
    private long size;
}

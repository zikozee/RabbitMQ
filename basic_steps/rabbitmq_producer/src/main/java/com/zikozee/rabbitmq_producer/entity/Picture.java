package com.zikozee.rabbitmq_producer.entity;

import lombok.*;

/**
 * @author : zikoz
 * @created : 04 Jun, 2021
 */

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Picture {

    private String name;
    private String type;
    private String source;
    private long size;
}

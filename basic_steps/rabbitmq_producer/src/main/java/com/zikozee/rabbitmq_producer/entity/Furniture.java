package com.zikozee.rabbitmq_producer.entity;

import lombok.*;

/**
 * @author : zikoz
 * @created : 05 Jun, 2021
 */

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Furniture {

    private String color;
    private String material;
    private String name;
    private int price;
}

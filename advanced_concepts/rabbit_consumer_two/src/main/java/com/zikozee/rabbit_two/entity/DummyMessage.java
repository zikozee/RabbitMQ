package com.zikozee.rabbit_two.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : zikoz
 * @created : 07 Jun, 2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DummyMessage {

    private String content;
    private int publishOrder;
}

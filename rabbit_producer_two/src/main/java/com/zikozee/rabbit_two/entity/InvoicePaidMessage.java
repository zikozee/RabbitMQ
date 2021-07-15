package com.zikozee.rabbit_two.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

/**
 * @author : zikoz
 * @created : 20 Jun, 2021
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InvoicePaidMessage {
    public static final String PATTERN = "yyyy-MM-dd";

    private String invoiceNumber;
    @JsonFormat(pattern = PATTERN)
    private LocalDate paidDate;
    private String paymentNumber;
}

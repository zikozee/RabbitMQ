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
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCreatedMessage {
    public static final String PATTERN = "yyyy-MM-dd";

    private double amount;
    @JsonFormat(pattern = PATTERN)
    private LocalDate createDate;
    private String currency;
    private String invoiceNumber;
}

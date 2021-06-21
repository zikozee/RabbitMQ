package com.zikozee.rabbit_two.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

import static com.zikozee.rabbit_two.entity.InvoicePaidMessage.PATTERN;

/**
 * @author : zikoz
 * @created : 21 Jun, 2021
 */

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCancelStatus {

    private boolean cancelStatus;

    @JsonFormat(pattern = PATTERN)
    private LocalDate cancelDate;

    private String invoiceNumber;
}

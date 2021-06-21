package com.zikozee.rabbit_two.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

import static com.zikozee.rabbit_two.entity.InvoiceCreatedMessage.PATTERN;

/**
 * @author : zikoz
 * @created : 20 Jun, 2021
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceCancelledMessage {

    @JsonFormat(pattern = PATTERN)
    private LocalDate cancelDate;
    private String invoiceNumber;
    private String reason;
}

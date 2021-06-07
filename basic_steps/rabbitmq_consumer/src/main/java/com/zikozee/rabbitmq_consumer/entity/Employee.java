package com.zikozee.rabbitmq_consumer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

/**
 * @author : zikoz
 * @created : 04 Jun, 2021
 */

@Getter @Setter
@NoArgsConstructor // required to convert JSON to java class
@AllArgsConstructor
@ToString
public class Employee {

    @JsonProperty("employee_id")
    private String employeeId;
    private String name;

    @JsonProperty("birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}

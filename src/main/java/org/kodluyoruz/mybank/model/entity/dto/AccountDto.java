package org.kodluyoruz.mybank.model.entity.dto;

import lombok.Data;
import org.kodluyoruz.mybank.model.entity.Customer;

import java.time.LocalDate;

@Data
public class AccountDto {
    private String name;
    private Long iban;
    private Double accountBalance;
    private String moneyType;
    private Long customerId;
}

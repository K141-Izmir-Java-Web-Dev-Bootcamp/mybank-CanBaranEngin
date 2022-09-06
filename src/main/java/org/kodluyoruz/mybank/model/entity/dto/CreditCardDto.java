package org.kodluyoruz.mybank.model.entity.dto;

import lombok.Data;


@Data
public class CreditCardDto {
    private String cvc;
    private double creditCardLimit;
    private Long customerId;
    private Long accountId;
    private String password;

}




package org.kodluyoruz.mybank.model.entity.dto;

import lombok.Data;


@Data
public class CreditCardDto {
    private String customerFirstName;
    private String customerLastName;
    private int cvc;
    private double creditCardLimit;
    private Long customerId;
    private Long accountId;

}




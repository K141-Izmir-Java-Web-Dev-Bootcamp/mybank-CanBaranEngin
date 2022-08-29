package org.kodluyoruz.mybank.model.entity.dto;

import lombok.Data;


@Data
public class DebitCardDto {
    private String customerFirstName;
    private String customerLastName;
    private int cvc;
    private Long customerId;
    private Long accountId;

}




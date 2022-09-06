package org.kodluyoruz.mybank.model.entity.dto;

import lombok.Data;


@Data
public class DebitCardDto {
    private String cvc;
    private Long customerId;
    private Long accountId;
    private String password;

}




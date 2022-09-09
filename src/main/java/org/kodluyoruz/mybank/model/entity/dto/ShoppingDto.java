package org.kodluyoruz.mybank.model.entity.dto;


import lombok.Data;
import org.kodluyoruz.mybank.model.entity.CardType;
@Data
public class ShoppingDto {
    private Double spending;
    private CardType cardType;
    private Long cardId;
    private String cardPassword;
}

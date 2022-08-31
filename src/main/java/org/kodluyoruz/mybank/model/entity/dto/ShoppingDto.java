package org.kodluyoruz.mybank.model.entity.dto;


import lombok.Data;
import org.kodluyoruz.mybank.model.entity.CardType;
@Data
public class ShoppingDto {
    private Long spending;
    private int cardType;
    private Long cardId;
}

package org.kodluyoruz.mybank.service.impl;


import org.kodluyoruz.mybank.model.entity.DebitCard;
import org.kodluyoruz.mybank.model.entity.dto.DebitCardDto;

public interface DebitCardServiceImpl {
    public DebitCard create(DebitCardDto debitCardDto);
}

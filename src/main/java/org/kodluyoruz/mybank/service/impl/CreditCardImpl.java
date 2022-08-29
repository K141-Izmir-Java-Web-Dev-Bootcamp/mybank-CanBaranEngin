package org.kodluyoruz.mybank.service.impl;

import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.dto.CreditCardDto;

public interface CreditCardImpl {
    public CreditCard create(CreditCardDto creditCardDto);
}

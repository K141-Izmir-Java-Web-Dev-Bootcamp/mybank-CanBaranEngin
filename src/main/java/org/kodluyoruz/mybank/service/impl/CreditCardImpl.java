package org.kodluyoruz.mybank.service.impl;

import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.dto.CreditCardDto;

public interface CreditCardImpl {
    public CreditCard create(CreditCardDto creditCardDto);
    public CreditCard getCreditCardbyId(Long id);
    public Double getDebtById(Long id);
    public Boolean payDebt(Long id,Double moneyValue,String password);
    public Boolean deleteCreditCardById(Long id);


}

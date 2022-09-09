package org.kodluyoruz.mybank.service.impl;


import org.kodluyoruz.mybank.model.entity.DebitCard;
import org.kodluyoruz.mybank.model.entity.dto.DebitCardDto;

import java.util.List;

public interface DebitCardServiceImpl {
    public DebitCard create(DebitCardDto debitCardDto);
    public List<DebitCard> getAll();
    public DebitCard getDebitCardById(Long id);
    public void deleteDebitCardById(Long id);
}

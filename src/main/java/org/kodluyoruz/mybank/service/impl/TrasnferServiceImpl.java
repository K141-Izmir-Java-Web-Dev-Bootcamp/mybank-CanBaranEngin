package org.kodluyoruz.mybank.service.impl;

import org.kodluyoruz.mybank.model.entity.Transfer;
import org.kodluyoruz.mybank.model.entity.dto.TransferDto;

public interface TrasnferServiceImpl {

    public void create(TransferDto transferDto);
    public Transfer getTransferById(Long id);

}

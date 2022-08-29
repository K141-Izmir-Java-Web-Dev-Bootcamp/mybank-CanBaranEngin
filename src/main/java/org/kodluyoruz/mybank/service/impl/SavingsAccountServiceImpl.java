package org.kodluyoruz.mybank.service.impl;

import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.SavingsAccount;
import org.kodluyoruz.mybank.model.entity.dto.AccountDto;

import java.util.List;

public interface SavingsAccountServiceImpl {
    public SavingsAccount create(AccountDto accountDto);

    public List<SavingsAccount > getAll();

}

package org.kodluyoruz.mybank.service.impl;

import org.kodluyoruz.mybank.model.entity.Account;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.dto.AccountDto;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;

import java.util.List;

public interface DepositAccountServiceImpl {
    public DepositAccount create(AccountDto accountDto);

    public List<DepositAccount> getAll();

    public DepositAccount getDepositAccountById(Long id);

    public DepositAccount getDepositAccountByIban(Long iban);

}

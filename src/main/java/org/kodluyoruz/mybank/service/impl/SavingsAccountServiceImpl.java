package org.kodluyoruz.mybank.service.impl;

import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.SavingsAccount;
import org.kodluyoruz.mybank.model.entity.dto.AccountDto;

import java.util.List;

public interface SavingsAccountServiceImpl {
    public SavingsAccount create(AccountDto accountDto);

    public List<SavingsAccount > getAll();
    public SavingsAccount getSavingsAccountById (Long id);
    public SavingsAccount getSavingsAccountByIban(Long iban);
    public List<SavingsAccount> getSavingsAccountByIdentityNumber(Long identityNumber);
    public void deleteSavingsAccountById(Long id);


}

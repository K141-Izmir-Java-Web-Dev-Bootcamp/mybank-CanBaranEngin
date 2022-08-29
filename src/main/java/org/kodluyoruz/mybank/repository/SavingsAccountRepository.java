package org.kodluyoruz.mybank.repository;

import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.SavingsAccount;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SavingsAccountRepository extends PagingAndSortingRepository<SavingsAccount,Long> {


}

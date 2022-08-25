package org.kodluyoruz.mybank.repository;

import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepositAccountRepository extends PagingAndSortingRepository<DepositAccount,Long> {


}

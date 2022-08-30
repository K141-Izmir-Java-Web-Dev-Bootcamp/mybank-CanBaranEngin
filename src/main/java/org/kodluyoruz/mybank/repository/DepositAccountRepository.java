package org.kodluyoruz.mybank.repository;

import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface DepositAccountRepository extends PagingAndSortingRepository<DepositAccount,Long> {

   Optional<DepositAccount> findDepositAccountByIban(Long iban);


}

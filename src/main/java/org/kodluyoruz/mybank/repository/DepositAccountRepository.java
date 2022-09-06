package org.kodluyoruz.mybank.repository;

import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface DepositAccountRepository extends JpaRepository<DepositAccount,Long> {

   Optional<DepositAccount> findDepositAccountByIban(Long iban);
   Optional<List<DepositAccount>> findDepositAccountByCustomerIdentityNumber(Long customerIdentityNumber);


   void deleteAllByCustomerId(Long id);
}

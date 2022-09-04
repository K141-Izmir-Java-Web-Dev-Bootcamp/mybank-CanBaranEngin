package org.kodluyoruz.mybank.repository;

import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.management.Query;

public interface CustomerRepository extends PagingAndSortingRepository<Customer,Long> {

    void deleteById(Long id);
}

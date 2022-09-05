package org.kodluyoruz.mybank.repository;

import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CreditCardRepository extends PagingAndSortingRepository <CreditCard,Long> {



}

package org.kodluyoruz.mybank.repository;

import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CreditCardRepository extends PagingAndSortingRepository <CreditCard,Long> {

}

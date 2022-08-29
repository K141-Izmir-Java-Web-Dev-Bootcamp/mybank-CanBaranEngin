package org.kodluyoruz.mybank.repository;

import org.kodluyoruz.mybank.model.entity.DebitCard;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DebitCardRepository extends PagingAndSortingRepository<DebitCard,Long> {

}

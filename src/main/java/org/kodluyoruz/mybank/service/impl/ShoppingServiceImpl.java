package org.kodluyoruz.mybank.service.impl;

import org.kodluyoruz.mybank.model.entity.Shopping;
import org.kodluyoruz.mybank.model.entity.dto.ShoppingDto;

public interface ShoppingServiceImpl {

    public void create(ShoppingDto shoppingDto);
    public Shopping getShoppingById(Long id);



}

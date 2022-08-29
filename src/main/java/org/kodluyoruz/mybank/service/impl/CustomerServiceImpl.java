package org.kodluyoruz.mybank.service.impl;

import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;

import java.util.List;

public interface CustomerServiceImpl {
    public Customer create(CustomerDto customerDto);
    public Customer getCustomerById(long id);
    public List<Customer> getCustomers();
    public CustomerDto updateCustomer(Long id,CustomerDto customerDto);
    public Boolean deleteCustomer(Long id);
}

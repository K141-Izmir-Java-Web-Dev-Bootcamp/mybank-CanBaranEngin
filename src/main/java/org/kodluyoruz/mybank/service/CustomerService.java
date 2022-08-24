package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;
import org.kodluyoruz.mybank.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public CustomerDto create(CustomerDto customerDto){

        Customer createdCustomer = modelMapper.map(customerDto,Customer.class);
        return modelMapper.map(customerRepository.save(createdCustomer),CustomerDto.class);
    }

    public Optional<Customer> getCustomerById(long customerId){
        return customerRepository.findById(customerId);
    }
}

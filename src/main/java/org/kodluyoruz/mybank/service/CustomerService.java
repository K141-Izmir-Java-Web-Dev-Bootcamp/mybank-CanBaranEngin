package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;
import org.kodluyoruz.mybank.repository.CustomerRepository;
import org.kodluyoruz.mybank.service.impl.CustomerServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService implements CustomerServiceImpl {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Customer create(CustomerDto customerDto){

        Customer createdCustomer = modelMapper.map(customerDto,Customer.class);
        return customerRepository.save(createdCustomer);
    }

    @Override
    public Customer getCustomerById(long id){
        Optional<Customer> getCustomer = customerRepository.findById(id);
        customerRepository.save(getCustomer.get());
        return getCustomer.get();
    }

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        return customers;
    }

    @Override
    public CustomerDto updateCustomer(Long id,CustomerDto customerDto) {
        Optional<Customer> customerResult = customerRepository.findById(id);
        if(customerResult.isPresent()){
            customerResult.get().setFirstName(customerDto.getFirstName());
            customerResult.get().setLastName(customerDto.getLastName());
            customerResult.get().setBirthday(customerDto.getBirthday());
            customerResult.get().setEmail(customerDto.getEmail());
            customerResult.get().setPassword(customerDto.getPassword());
            customerResult.get().setIdentityNumber(customerDto.getIdentityNumber());
            return modelMapper.map(customerRepository.save(customerResult.get()),CustomerDto.class);
        }

        return null;
    }

    @Override
    public Boolean deleteCustomer(Long id) {
        Optional<Customer> customers = customerRepository.findById(id);

        if(customers.isPresent()) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }


}

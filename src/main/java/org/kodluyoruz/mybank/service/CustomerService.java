package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;
import org.kodluyoruz.mybank.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public CustomerDto getCustomerById(long id){
        Optional<Customer> getCustomer = customerRepository.findById(id);
        return modelMapper.map(customerRepository.save(getCustomer.get()),CustomerDto.class);
    }

    public List<CustomerDto> getCustomers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        return customers.stream().map(customer -> modelMapper.map(customer,CustomerDto.class)).collect(Collectors.toList());
    }


    public Boolean deleteCustomer(Long id) {
        Optional<Customer> customers = customerRepository.findById(id);

        if(customers.isPresent()) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

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
}

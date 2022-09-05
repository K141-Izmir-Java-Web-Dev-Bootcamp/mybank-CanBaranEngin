package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.SavingsAccount;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;
import org.kodluyoruz.mybank.repository.CustomerRepository;
import org.kodluyoruz.mybank.repository.DepositAccountRepository;
import org.kodluyoruz.mybank.service.impl.CustomerServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements CustomerServiceImpl {

    private final CustomerRepository customerRepository;
    @Autowired
    DepositAccountService depositAccountService;

    @Autowired
    SavingsAccountService savingsAccountService;

    @Autowired
    DepositAccountRepository depositAccountRepository;

    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Customer create(CustomerDto customerDto){
        Customer createdCustomer = modelMapper.map(customerDto,Customer.class);
        long generatedLong = 1000L + (long) (Math.random() * (9000L - 1000L));
        createdCustomer.setIdentityNumber(generatedLong);
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
            return modelMapper.map(customerRepository.save(customerResult.get()),CustomerDto.class);
        }

        return null;
    }

    @Override
    public Boolean deleteCustomer(Long id) {
        Optional<Customer> customers = customerRepository.findById(id);
        List<DepositAccount> depositAccounts = depositAccountService.getDepositAccountByIdentityNumber(customers.get().getIdentityNumber());
        List<SavingsAccount> savingsAccounts = savingsAccountService.getDepositAccountByIdentityNumber(customers.get().getIdentityNumber());
        if(customers.isPresent() && savingsAccounts.isEmpty()){
            for (int i = 0; i < depositAccounts.size() ; i++) {
                if(depositAccounts.get(i).getAccountBalance()!=0){
                    i=depositAccounts.size()-1;
                }
                else {
                    if(i==depositAccounts.size()-1){
                        for (int s = 0; s <depositAccounts.size() ; s++) {
                            depositAccountRepository.delete(depositAccounts.get(s));
                        }
                        customerRepository.deleteById(id);
                        return true;
                    }

                }
            }

        }
        return false;
    }
}

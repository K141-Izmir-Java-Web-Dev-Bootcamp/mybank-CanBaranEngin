package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.SavingsAccount;
import org.kodluyoruz.mybank.model.entity.dto.AccountDto;
import org.kodluyoruz.mybank.repository.CustomerRepository;

import org.kodluyoruz.mybank.repository.SavingsAccountRepository;

import org.kodluyoruz.mybank.service.impl.SavingsAccountServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingsAccountService implements SavingsAccountServiceImpl {

    private final SavingsAccountRepository savingsAccountRepository;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public SavingsAccountService(SavingsAccountRepository savingsAccountRepository, CustomerRepository customerRepository, CustomerService customerService, ModelMapper modelMapper) {
        this.savingsAccountRepository= savingsAccountRepository;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }
    @Override
    public SavingsAccount create(AccountDto accountDto){
        SavingsAccount savingsAccount = modelMapper.map(accountDto,SavingsAccount.class);
        savingsAccount.setCustomer(customerService.getCustomerById(accountDto.getCustomerId()));
        return savingsAccountRepository.save(savingsAccount);
    }



    @Override
    public List<SavingsAccount> getAll() {
        List<SavingsAccount> savingsAccounts = (List<SavingsAccount>) savingsAccountRepository.findAll();
        return savingsAccounts.stream().collect(Collectors.toList());
    }
}
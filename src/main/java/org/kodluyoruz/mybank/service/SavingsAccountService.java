package org.kodluyoruz.mybank.service;

import lombok.extern.slf4j.Slf4j;
import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.SavingsAccount;
import org.kodluyoruz.mybank.model.entity.dto.AccountDto;
import org.kodluyoruz.mybank.repository.CustomerRepository;

import org.kodluyoruz.mybank.repository.SavingsAccountRepository;

import org.kodluyoruz.mybank.service.impl.SavingsAccountServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        savingsAccount.setCustomerIdentityNumber(customerService.getCustomerById(accountDto.getCustomerId()).getIdentityNumber());
        savingsAccount.setCustomer(customerService.getCustomerById(accountDto.getCustomerId()));
        savingsAccount.setCreatedDate(LocalDate.now());
        return savingsAccountRepository.save(savingsAccount);
    }



    @Override
    public List<SavingsAccount> getAll() {
        List<SavingsAccount> savingsAccounts = (List<SavingsAccount>) savingsAccountRepository.findAll();
        return savingsAccounts.stream().collect(Collectors.toList());
    }
    @Override
    public SavingsAccount getSavingsAccountById (Long id){
        Optional<SavingsAccount> getSavingsAccount = savingsAccountRepository.findById(id);
        return getSavingsAccount.orElseThrow(() -> new EntityNotFoundException("SavingsAccount"));
    }
    @Override
    public SavingsAccount getSavingsAccountByIban(Long iban) {
        Optional<SavingsAccount> getSavingsAccountByIban = savingsAccountRepository.findSavingsAccountByIban(iban);
        return getSavingsAccountByIban.orElse(null);

    }
    @Override
    public List<SavingsAccount> getSavingsAccountByIdentityNumber(Long identityNumber) {
        Optional<List<SavingsAccount>> getSavingsAccountByIdentityNumber = savingsAccountRepository.findSavingsAccountByCustomerIdentityNumber(identityNumber);
        return getSavingsAccountByIdentityNumber.orElse(null);
    }
    @Override
    public void deleteSavingsAccountById(Long id) {
        Optional<SavingsAccount> savingsAccount = savingsAccountRepository.findById(id);
        if(savingsAccount.isPresent() && savingsAccount.get().getAccountBalance()==0){
            savingsAccountRepository.deleteById(id);
        }
        else{
            log.error("The savingsAccount to be deleted does not exist");
            throw new EntityNotFoundException("SavingsAccount");
        }

    }
}

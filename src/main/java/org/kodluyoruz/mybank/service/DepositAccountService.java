package org.kodluyoruz.mybank.service;

import lombok.extern.slf4j.Slf4j;
import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.Account;
import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.dto.AccountDto;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;
import org.kodluyoruz.mybank.repository.CustomerRepository;
import org.kodluyoruz.mybank.repository.DepositAccountRepository;
import org.kodluyoruz.mybank.service.impl.DepositAccountServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DepositAccountService implements DepositAccountServiceImpl {

    private final DepositAccountRepository depositAccountRepository;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public DepositAccountService(DepositAccountRepository depositAccountRepository, CustomerRepository customerRepository, CustomerService customerService, ModelMapper modelMapper) {
        this.depositAccountRepository = depositAccountRepository;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }
    @Override
    public DepositAccount create(AccountDto accountDto){
        DepositAccount depositAccount = modelMapper.map(accountDto,DepositAccount.class);
        long generatedLong = 100000000L + (long) (Math.random() * (900000000L - 100000000L));
        depositAccount.setIban(generatedLong);
        depositAccount.setCustomerIdentityNumber(customerService.getCustomerById(accountDto.getCustomerId()).getIdentityNumber());
        depositAccount.setCustomer(customerService.getCustomerById(accountDto.getCustomerId()));
        depositAccount.setCreatedDate(LocalDate.now());
        return depositAccountRepository.save(depositAccount);
    }

    @Override
    public List<DepositAccount> getAll(){
        return depositAccountRepository.findAll();
    }

    @Override
    public DepositAccount getDepositAccountById(Long id) {
        Optional<DepositAccount> getDepositAccount = depositAccountRepository.findById(id);
        return getDepositAccount.orElseThrow(() -> new EntityNotFoundException("DepositAccount"));

    }
    @Override
    public DepositAccount getDepositAccountByIban(Long iban) {
        Optional<DepositAccount> getDepositAccountByIban = depositAccountRepository.findDepositAccountByIban(iban);
        return getDepositAccountByIban.orElse(null);
    }

    public List<DepositAccount> getDepositAccountByIdentityNumber(Long identityNumber) {
        Optional<List<DepositAccount>> getDepositAccountByIdentityNumber = depositAccountRepository.findDepositAccountByCustomerIdentityNumber(identityNumber);
        return getDepositAccountByIdentityNumber.orElse(null);
    }
}

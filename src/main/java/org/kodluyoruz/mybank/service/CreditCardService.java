package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.dto.CreditCardDto;
import org.kodluyoruz.mybank.repository.CreditCardRepository;
import org.kodluyoruz.mybank.repository.DepositAccountRepository;
import org.kodluyoruz.mybank.service.impl.CreditCardImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditCardService implements CreditCardImpl {

    private final CreditCardRepository creditCardRepository;
    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final DepositAccountService depositAccountService;

    private final DepositAccountRepository depositAccountRepository;

    public CreditCardService(CreditCardRepository creditCardRepository, ModelMapper modelMapper, CustomerService customerService, DepositAccountService depositAccountService, DepositAccountRepository depositAccountRepository) {
        this.creditCardRepository = creditCardRepository;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.depositAccountService = depositAccountService;
        this.depositAccountRepository = depositAccountRepository;
    }


    @Override
    public CreditCard create(CreditCardDto creditCardDto) {
        CreditCard creditCards = modelMapper.map(creditCardDto,CreditCard.class);
        creditCards.setCustomer(customerService.getCustomerById(creditCardDto.getCustomerId()));
        creditCards.setDepositAccount(depositAccountService.getDepositAccountById(creditCardDto.getAccountId()));
        creditCards.setCustomerFirstName(customerService.getCustomerById(creditCardDto.getCustomerId()).getFirstName());
        creditCards.setCustomerLastName(customerService.getCustomerById(creditCardDto.getCustomerId()).getLastName());
        //depositAccountService.getDepositAccountById(creditCardDto.getAccountId()).setCreditCard(creditCards);

        return creditCardRepository.save(creditCards);
    }
    @Override
    public CreditCard getCreditCardbyId(Long id){
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        return creditCard.orElseThrow(()-> new EntityNotFoundException("CreditCard"));
    }

    public Double getDebtById(Long id) {
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        return creditCard.map(CreditCard::getCreditCardDebtValue).orElse(null);
    }


    @Override
    public Boolean payDebt(Long id,Double moneyValue,String password) {
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        DepositAccount depositAccount = creditCard.get().getDepositAccount();

        if(creditCard.isPresent() && creditCard.get().getPassword().equals(password)){
            creditCard.get().setCreditCardDebtValue(creditCard.get().getCreditCardDebtValue()-moneyValue);
            creditCard.get().setCreditCardLimit(creditCard.get().getCreditCardLimit()+moneyValue);
            creditCardRepository.save(creditCard.get());
            depositAccount.setAccountBalance(depositAccount.getAccountBalance()-moneyValue);
            depositAccountRepository.save(depositAccount);


            return true;
        }

        return false;


    }
}

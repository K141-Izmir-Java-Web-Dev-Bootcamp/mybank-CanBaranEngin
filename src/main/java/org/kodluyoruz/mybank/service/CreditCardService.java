package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.dto.CreditCardDto;
import org.kodluyoruz.mybank.repository.CreditCardRepository;
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

    public CreditCardService(CreditCardRepository creditCardRepository, ModelMapper modelMapper, CustomerService customerService, DepositAccountService depositAccountService) {
        this.creditCardRepository = creditCardRepository;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.depositAccountService = depositAccountService;
    }


    @Override
    public CreditCard create(CreditCardDto creditCardDto) {
        CreditCard creditCards = modelMapper.map(creditCardDto,CreditCard.class);
        creditCards.setCustomer(customerService.getCustomerById(creditCardDto.getCustomerId()));
        creditCards.setDepositAccount(depositAccountService.getDepositAccountById(creditCardDto.getAccountId()));
        creditCards.setCustomerFirstName(customerService.getCustomerById(creditCardDto.getCustomerId()).getFirstName());
        creditCards.setCustomerLastName(customerService.getCustomerById(creditCardDto.getCustomerId()).getLastName());

        return creditCardRepository.save(creditCards);
    }


    public Double getDebtById(Long id) {
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        return creditCard.map(CreditCard::getCreditCardDebtValue).orElse(null);
    }

    public Boolean payDebt(Long id,Double moneyValue) {
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        DepositAccount depositAccount = creditCard.get().getDepositAccount();

        if(creditCard.isPresent()){
            creditCard.get().setCreditCardDebtValue(creditCard.get().getCreditCardDebtValue()-moneyValue);
            depositAccount.setAccountBalance(depositAccount.getAccountBalance()-moneyValue);
            return true;
        }

        return false;

    }
}

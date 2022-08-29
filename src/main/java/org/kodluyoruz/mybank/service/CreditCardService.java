package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.dto.CreditCardDto;
import org.kodluyoruz.mybank.repository.CreditCardRepository;
import org.kodluyoruz.mybank.service.impl.CreditCardImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

        return creditCardRepository.save(creditCards);
    }



}

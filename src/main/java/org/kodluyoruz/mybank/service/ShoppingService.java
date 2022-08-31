package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.dto.ShoppingDto;
import org.kodluyoruz.mybank.repository.CreditCardRepository;
import org.kodluyoruz.mybank.repository.DepositAccountRepository;
import org.kodluyoruz.mybank.service.impl.ShoppingServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ShoppingService implements ShoppingServiceImpl {

    private final DepositAccountService depositAccountService;
    private final DepositAccountRepository depositAccountRepository;
    private final CreditCardService creditCardService;

    private final DebitCardService debitCardService;
    private final CreditCardRepository creditCardRepository;
    private final ModelMapper modelMapper;

    public ShoppingService(DepositAccountService depositAccountService, DepositAccountRepository depositAccountRepository, CreditCardService creditCardService, DebitCardService debitCardService, CreditCardRepository creditCardRepository, ModelMapper modelMapper) {
        this.depositAccountService = depositAccountService;
        this.depositAccountRepository = depositAccountRepository;
        this.creditCardService = creditCardService;
        this.debitCardService = debitCardService;
        this.creditCardRepository = creditCardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(ShoppingDto shoppingDto) {


        CreditCard creditCard =creditCardService.getCreditCardbyId(shoppingDto.getCardId());
        if(shoppingDto.getCardType()==1){
            creditCard.setCreditCardDebtValue(creditCardService.getCreditCardbyId(shoppingDto.getCardId()).getCreditCardDebtValue()+shoppingDto.getSpending());
            creditCard.setCreditCardLimit(creditCard.getCreditCardLimit()-shoppingDto.getSpending());
            creditCardRepository.save(creditCard);
        }
        else {
            DepositAccount depositAccount = depositAccountService.getDepositAccountById(debitCardService.getCreditCardbyId(shoppingDto.getCardId()).getDepositAccount().getId());
            depositAccount.setAccountBalance(depositAccount.getAccountBalance()-shoppingDto.getSpending());
            depositAccountRepository.save(depositAccount);
        }




    }
}

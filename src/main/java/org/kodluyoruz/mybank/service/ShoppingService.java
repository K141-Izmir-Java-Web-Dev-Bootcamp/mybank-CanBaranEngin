package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.Shopping;
import org.kodluyoruz.mybank.model.entity.dto.ShoppingDto;
import org.kodluyoruz.mybank.repository.CreditCardRepository;
import org.kodluyoruz.mybank.repository.DepositAccountRepository;
import org.kodluyoruz.mybank.repository.ShoppingRepository;
import org.kodluyoruz.mybank.service.impl.ShoppingServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingService implements ShoppingServiceImpl {

    private final DepositAccountService depositAccountService;
    private final DepositAccountRepository depositAccountRepository;
    private final CreditCardService creditCardService;

    private final ShoppingRepository shoppingRepository;

    private final CustomerService customerService;

    private final DebitCardService debitCardService;
    private final CreditCardRepository creditCardRepository;
    private final ModelMapper modelMapper;

    public ShoppingService(DepositAccountService depositAccountService, DepositAccountRepository depositAccountRepository, CreditCardService creditCardService, ShoppingRepository shoppingRepository, CustomerService customerService, DebitCardService debitCardService, CreditCardRepository creditCardRepository, ModelMapper modelMapper) {
        this.depositAccountService = depositAccountService;
        this.depositAccountRepository = depositAccountRepository;
        this.creditCardService = creditCardService;
        this.shoppingRepository = shoppingRepository;
        this.customerService = customerService;
        this.debitCardService = debitCardService;
        this.creditCardRepository = creditCardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(ShoppingDto shoppingDto) {
        Shopping shopping = modelMapper.map(shoppingDto,Shopping.class);
        CreditCard creditCard =creditCardService.getCreditCardbyId(shoppingDto.getCardId());
        if(shoppingDto.getCardType()==1){
            creditCard.setCreditCardDebtValue(creditCardService.getCreditCardbyId(shoppingDto.getCardId()).getCreditCardDebtValue()+shoppingDto.getSpending());
            creditCard.setCreditCardLimit(creditCard.getCreditCardLimit()-shoppingDto.getSpending());
            shopping.setCustomer(creditCard.getCustomer());
            creditCardRepository.save(creditCard);
            shoppingRepository.save(shopping);
        }
        else {
            DepositAccount depositAccount = depositAccountService.getDepositAccountById(debitCardService.getCreditCardById(shoppingDto.getCardId()).getDepositAccount().getId());
            depositAccount.setAccountBalance(depositAccount.getAccountBalance()-shoppingDto.getSpending());
            shopping.setCustomer(depositAccount.getCustomer());
            depositAccountRepository.save(depositAccount);
            shoppingRepository.save(shopping);
        }

    }

    public Shopping getShoppingById(Long id) {
        Optional<Shopping> shopping = shoppingRepository.findById(id);
        return shopping.orElseThrow(()-> new EntityNotFoundException("Shopping"));

    }
}

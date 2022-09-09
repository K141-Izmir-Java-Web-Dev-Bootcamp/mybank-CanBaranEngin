package org.kodluyoruz.mybank.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kodluyoruz.mybank.exception.ArithmeticException;
import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.*;
import org.kodluyoruz.mybank.model.entity.dto.ShoppingDto;
import org.kodluyoruz.mybank.repository.CreditCardRepository;
import org.kodluyoruz.mybank.repository.DepositAccountRepository;
import org.kodluyoruz.mybank.repository.ShoppingRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingServiceTest {

    @Mock
    private DepositAccountService depositAccountService;
    @Mock
    private DepositAccountRepository depositAccountRepository;
    @Mock
    private CreditCardService creditCardService;
    @Mock
    private ShoppingRepository shoppingRepository;
    @Mock
    private DebitCardService debitCardService;
    @Mock
    private CreditCardRepository creditCardRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ShoppingService underTest;


    @Test
    void WhenCreateShoppingCalledWithValidRequestAndIfSelectCardTypeCreditCardAndCreditCardLimitEnoughAndGivenPasswordTrue_ShoppingProcessShouldSuccess()  {
        Shopping shopping = new Shopping();
        shopping.setCardType(CardType.CREDITCARD);
        shopping.setSpending(100L);
        ShoppingDto shoppingDto = new ShoppingDto();
        shoppingDto.setCardType(CardType.CREDITCARD);
        shoppingDto.setCardId(1L);
        shoppingDto.setSpending(100.0);
        shoppingDto.setCardPassword("1234");
        CreditCard creditCard = new CreditCard();
        creditCard.setId(1L);
        creditCard.setCreditCardLimit(1000.0);
        creditCard.setPassword("1234");
        when(modelMapper.map(shoppingDto,Shopping.class)).thenReturn(shopping);
        when(creditCardService.getCreditCardbyId(shoppingDto.getCardId())).thenReturn(creditCard);
        when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
        when(shoppingRepository.save(shopping)).thenReturn(shopping);
        underTest.create(shoppingDto);

    }

    @Test
    void WhenCreateShoppingCalledWithValidRequestAndIfSelectCardTypeCreditCardAndCreditCardLimitNotEnoughAndGivenPasswordTrue_ShoppingProcessShouldReturnArithmeticException()  {
        Shopping shopping = new Shopping();
        shopping.setCardType(CardType.CREDITCARD);
        shopping.setSpending(100L);
        ShoppingDto shoppingDto = new ShoppingDto();
        shoppingDto.setCardType(CardType.CREDITCARD);
        shoppingDto.setCardId(1L);
        shoppingDto.setSpending(100.0);
        shoppingDto.setCardPassword("1234");
        CreditCard creditCard = new CreditCard();
        creditCard.setId(1L);
        creditCard.setCreditCardLimit(10.0);
        creditCard.setPassword("1234");
        when(modelMapper.map(shoppingDto,Shopping.class)).thenReturn(shopping);
        when(creditCardService.getCreditCardbyId(shoppingDto.getCardId())).thenReturn(creditCard);
        assertThrows(ArithmeticException.class,()->underTest.create(shoppingDto));

    }

    @Test
    void WhenCreateShoppingCalledWithValidRequestAndIfSelectCardTypeDebitCardAndDepositAccountBalanceEnoughAndGivenPasswordTrue_ShoppingProcessShouldSuccess()  {
        Shopping shopping = new Shopping();
        shopping.setCardType(CardType.DEBITCARD);
        shopping.setSpending(100L);
        ShoppingDto shoppingDto = new ShoppingDto();
        shoppingDto.setCardType(CardType.DEBITCARD);
        shoppingDto.setCardId(1L);
        shoppingDto.setSpending(100.0);
        shoppingDto.setCardPassword("1234");
        DebitCard debitCard = new DebitCard();
        debitCard.setId(1L);
        debitCard.setPassword("1234");
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setAccountBalance(1000.0);
        depositAccount.setId(1L);
        debitCard.setDepositAccount(depositAccount);
        when(modelMapper.map(shoppingDto,Shopping.class)).thenReturn(shopping);
        when(debitCardService.getDebitCardById(shoppingDto.getCardId())).thenReturn(debitCard);
        when(depositAccountService.getDepositAccountById(1L)).thenReturn(depositAccount);
        when(depositAccountRepository.save(depositAccount)).thenReturn(depositAccount);
        when(shoppingRepository.save(shopping)).thenReturn(shopping);
        underTest.create(shoppingDto);

    }

    @Test
    void WhenCreateShoppingCalledWithValidRequestAndIfSelectCardTypeDebitCardAndDepositAccountBalanceNotEnoughAndGivenPasswordTrue_ShoppingProcessShouldReturnArithmeticException()  {
        Shopping shopping = new Shopping();
        shopping.setCardType(CardType.DEBITCARD);
        shopping.setSpending(100L);
        ShoppingDto shoppingDto = new ShoppingDto();
        shoppingDto.setCardType(CardType.DEBITCARD);
        shoppingDto.setCardId(1L);
        shoppingDto.setSpending(100.0);
        shoppingDto.setCardPassword("1234");
        DebitCard debitCard = new DebitCard();
        debitCard.setId(1L);
        debitCard.setPassword("1234");
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setAccountBalance(10.0);
        depositAccount.setId(1L);
        debitCard.setDepositAccount(depositAccount);
        when(modelMapper.map(shoppingDto,Shopping.class)).thenReturn(shopping);
        when(debitCardService.getDebitCardById(shoppingDto.getCardId())).thenReturn(debitCard);
        when(depositAccountService.getDepositAccountById(1L)).thenReturn(depositAccount);
        assertThrows(ArithmeticException.class,()->underTest.create(shoppingDto));

    }

    @Test
    void WhenCallGetShoppingById_ItShouldReturnShopping() {
        Shopping shopping = new Shopping();
        when(shoppingRepository.findById(1L)).thenReturn(Optional.of(shopping));
        assertEquals(shopping,underTest.getShoppingById(1L));
    }

    @Test
    void ShouldThrowEntityNotFoundExceptionWhenThereIsNoCreditCardBeforeGetCreditCardById() {
        Shopping shopping = new Shopping();
        when(shoppingRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> underTest.getShoppingById(1L));
    }


}
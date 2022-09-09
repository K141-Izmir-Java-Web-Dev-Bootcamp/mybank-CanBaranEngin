package org.kodluyoruz.mybank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.DebitCard;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.dto.CreditCardDto;
import org.kodluyoruz.mybank.repository.CreditCardRepository;
import org.kodluyoruz.mybank.repository.DepositAccountRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest {

    @Mock
    private CreditCardRepository creditCardRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CustomerService customerService;
    @Mock
    private DepositAccountService depositAccountService;
    @Mock
    private DepositAccountRepository depositAccountRepository;
    @InjectMocks
    private CreditCardService underTest;


    @Test
    void WhenCreateCreditCardCalledWithValidRequest_ItShouldReturnValidCreditCard() {
        Customer customer = new Customer();
        customer.setFirstName("can");
        customer.setLastName("baran");
        DepositAccount depositAccount=new DepositAccount();
        CreditCard creditCard = new CreditCard();
        CreditCardDto creditCardDto = new CreditCardDto();
        creditCardDto.setAccountId(1L);
        creditCardDto.setCustomerId(1L);
        when(modelMapper.map(creditCardDto,CreditCard.class)).thenReturn(creditCard);
        when(customerService.getCustomerById(creditCardDto.getCustomerId())).thenReturn(customer);
        when(depositAccountService.getDepositAccountById(creditCardDto.getAccountId())).thenReturn(depositAccount);
        when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
        assertEquals(creditCard,underTest.create(creditCardDto));
    }

    @Test
    void WhenCallGetCreditCardsById_ItShouldReturnCreditCard() {
        CreditCard creditCard= new CreditCard();
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        assertEquals(creditCard,underTest.getCreditCardbyId(1L));
    }

    @Test
    void ShouldThrowEntityNotFoundExceptionWhenThereIsNoCreditCardBeforeGetCreditCardById() {
        CreditCard creditCard= new CreditCard();
        when(creditCardRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> underTest.getCreditCardbyId(1L));
    }

    @Test
    void WhenCallGetCreditCardDebtById_ItShouldReturnCreditCardDebt() {
        CreditCard creditCard= new CreditCard();
        creditCard.setId(1L);
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        Double expectedCreditCardDebtValue=creditCard.getCreditCardDebtValue();
        assertEquals(expectedCreditCardDebtValue,underTest.getDebtById(1L));
    }

    @Test
    void WhenCallGetCreditCardDebtByIdAndThereIsNoCreditCardDebt_ItShouldReturnNull() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.empty());
        assertNull(underTest.getDebtById(1L));
    }

    @Test
    void WhenCallPayDebtAndIfGivenPasswordIsCorrect_ItShouldReturnTrue() {
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setAccountBalance(300.0);
        CreditCard creditCard = new CreditCard();
        creditCard.setPassword("1234");
        creditCard.setDepositAccount(depositAccount);
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
        when(depositAccountRepository.save(depositAccount)).thenReturn(depositAccount);
        assertEquals(true,underTest.payDebt(1L,100.0,"1234"));

    }

    @Test
    void WhenCallPayDebtAndIfGivenPasswordIsNotCorrect_ItShouldReturnFalse() {
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setAccountBalance(300.0);
        CreditCard creditCard = new CreditCard();
        creditCard.setPassword("1235");
        creditCard.setDepositAccount(depositAccount);
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        assertEquals(false,underTest.payDebt(1L,100.0,"1234"));

    }

    @Test
    void WhenCallPayDebtAndIfThereIsNoCreditCard_ItShouldReturnFalse() {
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setAccountBalance(300.0);
        CreditCard creditCard = new CreditCard();
        creditCard.setPassword("1235");
        creditCard.setDepositAccount(depositAccount);
        when(creditCardRepository.findById(1L)).thenReturn(Optional.empty());
        assertEquals(false,underTest.payDebt(1L,100.0,"1234"));

    }

    @Test
    void WhenCallDeleteCreditCardById_ItShouldDeleteCreditCards(){
        CreditCard creditCard = new CreditCard();
        creditCard.setId(1L);
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        doNothing().when(creditCardRepository).deleteById(1L);
        underTest.deleteCreditCardById(1L);
        verify(creditCardRepository,times(1)).deleteById(1L);
    }

    @Test
    void ShouldThrowEntityNotFoundExceptionWhenThereIsNoCreditCardBeforeDeleteCreditCardById(){
        when(creditCardRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> underTest.deleteCreditCardById(1L));
    }

}
package org.kodluyoruz.mybank.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Enabled;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.DebitCard;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.dto.AccountDto;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;
import org.kodluyoruz.mybank.repository.CustomerRepository;
import org.kodluyoruz.mybank.repository.DepositAccountRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Executable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositAccountServiceTest {

    @Mock
    public DepositAccountRepository depositAccountRepository;

    @Mock
    public ModelMapper modelMapper;

    @Mock
    public CustomerService customerService;

    @Mock
    public CustomerRepository customerRepository;

    @InjectMocks
    public DepositAccountService underTest;

    @Test
    void  whenCreateDepositAccountCalledWithValidRequest_ItShouldReturnValidDepositAccount() {
        DepositAccount depositAccount = new DepositAccount();
        Customer customer = new Customer();
        AccountDto accountDto = new AccountDto();
        accountDto.setCustomerId(1L);
        when(modelMapper.map(accountDto,DepositAccount.class)).thenReturn(depositAccount);
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(depositAccountRepository.save(depositAccount)).thenReturn(depositAccount);
        DepositAccount result = underTest.create(accountDto);
        assertEquals(result,depositAccount);
    }
    @Test
    void WhenCallGetDepositAccounts_ItShouldReturnAllDepositAccounts() {
        List<DepositAccount> depositAccount = getSampleDepositList();
        when(depositAccountRepository.findAll()).thenReturn(depositAccount);
        assertEquals(depositAccount,underTest.getAll());

    }

    @Test
    void WhenCallGetDepositAccountsById_ItShouldReturnDepositAccount() {
        DepositAccount depositAccount = new DepositAccount();
        when(depositAccountRepository.findById(1L)).thenReturn(Optional.of(depositAccount));
        assertEquals(depositAccount,underTest.getDepositAccountById(1L));

    }
    @Test
    void shouldThrowEntityNotFoundExceptionWhenThereIsNoDepositAccountBeforeGetDepositAccountById() {
        DepositAccount depositAccount = new DepositAccount();
        when(depositAccountRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> underTest.getDepositAccountById(1L));
    }

    @Test
    void WhenCallGetDepositAccountsByIban_ItShouldReturnDepositAccount()  {
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setIban(1234L);
        when(depositAccountRepository.findDepositAccountByIban(1234L)).thenReturn(Optional.of(depositAccount));
        assertEquals(depositAccount,underTest.getDepositAccountByIban(1234L));

    }

    @Test
    void WhenCallGetDepositAccountsByIban_ItShouldReturnNull()  {
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setIban(1234L);
        when(depositAccountRepository.findDepositAccountByIban(1234L)).thenReturn(Optional.empty());
        assertNull(underTest.getDepositAccountByIban(1234L));
    }

    @Test
    void WhenCallGetDepositAccountsByIdentityNumber_ItShouldReturnDepositAccount()  {
        List<DepositAccount> depositAccount = getSampleDepositList();
        when(depositAccountRepository.findDepositAccountByCustomerIdentityNumber(any())).thenReturn(Optional.of(depositAccount));
        assertEquals(depositAccount,underTest.getDepositAccountByIdentityNumber(any()));
    }

    @Test
    void WhenCallGetDepositAccountsByIdentityNumber_ItShouldReturnNull()  {
        List<DepositAccount> depositAccount = getSampleDepositList();
        when(depositAccountRepository.findDepositAccountByCustomerIdentityNumber(any())).thenReturn(Optional.empty());
        assertNull(underTest.getDepositAccountByIdentityNumber(any()));
    }

    @Test
    void WhenCallDeleteDepositAccountById_ItShouldDeleteDepositAccount(){
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setId(1L);
        depositAccount.setAccountBalance(0.0);
        when(depositAccountRepository.findById(1L)).thenReturn(Optional.of(depositAccount));
        doNothing().when(depositAccountRepository).deleteById(1L);
        underTest.deleteDepositAccountById(1L);
        verify(depositAccountRepository,times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenThereIsNoDepositAccountBeforeDeleteDepositAccountById(){
        when(depositAccountRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> underTest.deleteDepositAccountById(1L));
    }






    private List<DepositAccount> getSampleDepositList(){
        List<DepositAccount> depositList = new ArrayList<>();
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setCustomerIdentityNumber(123L);
        depositAccount.setAccountBalance(0.0);
        DepositAccount depositAccount1 = new DepositAccount();
        depositAccount1.setCustomerIdentityNumber(123l);
        depositAccount1.setAccountBalance(0.0);
        depositList.add(depositAccount);
        depositList.add(depositAccount1);
        return depositList;
    }
}
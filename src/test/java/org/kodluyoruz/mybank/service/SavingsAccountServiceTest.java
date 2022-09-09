package org.kodluyoruz.mybank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.SavingsAccount;
import org.kodluyoruz.mybank.model.entity.dto.AccountDto;
import org.kodluyoruz.mybank.repository.SavingsAccountRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SavingsAccountServiceTest {

    @Mock
    private SavingsAccountRepository savingsAccountRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SavingsAccountService underTest;


    @Test
    void  whenCreateSavingsAccountCalledWithValidRequest_ItShouldReturnValidSavingsAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        Customer customer = new Customer();
        AccountDto accountDto = new AccountDto();
        accountDto.setCustomerId(1L);
        when(modelMapper.map(accountDto,SavingsAccount.class)).thenReturn(savingsAccount);
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(savingsAccountRepository.save(savingsAccount)).thenReturn(savingsAccount);
        SavingsAccount result = underTest.create(accountDto);
        assertEquals(result,savingsAccount);
    }

    @Test
    void WhenCallGetSavingsAccounts_ItShouldReturnAllSavingsAccounts() {
        List<SavingsAccount> savingsAccounts = getSampleSavingsList();
        when(savingsAccountRepository.findAll()).thenReturn(savingsAccounts);
        assertEquals(savingsAccounts,underTest.getAll());

    }

    @Test
    void WhenCallGetSavingsAccountById_ItShouldReturnSavingsAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        when(savingsAccountRepository.findById(1L)).thenReturn(Optional.of(savingsAccount));
        assertEquals(savingsAccount,underTest.getSavingsAccountById(1L));

    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenThereIsNoSavingsAccountBeforeGetSavingsAccountById() {
        SavingsAccount savingsAccount = new SavingsAccount();
        when(savingsAccountRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> underTest.getSavingsAccountById(1L));
    }

    @Test
    void WhenCallGetSavingsAccountByIban_ItShouldReturnSavingsAccount()  {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setIban(1234L);
        when(savingsAccountRepository.findSavingsAccountByIban(1234L)).thenReturn(Optional.of(savingsAccount));
        assertEquals(savingsAccount,underTest.getSavingsAccountByIban(1234L));
    }

    @Test
    void WhenCallGetSavingsAccountByIban_ItShouldReturnNull()  {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setIban(1234L);
        when(savingsAccountRepository.findSavingsAccountByIban(1234L)).thenReturn(Optional.empty());
        assertNull(underTest.getSavingsAccountByIban(1234L));
    }

    @Test
    void WhenCallGetSavingsAccountByIdentityNumber_ItShouldReturnSavingsAccount()  {
        List<SavingsAccount> savingsAccounts = getSampleSavingsList();
        when(savingsAccountRepository.findSavingsAccountByCustomerIdentityNumber(any())).thenReturn(Optional.of(savingsAccounts));
        assertEquals(savingsAccounts,underTest.getSavingsAccountByIdentityNumber(any()));
    }

    @Test
    void WhenCallGetSavingsAccountByIdentityNumber_ItShouldReturnNull()  {
        List<SavingsAccount> savingsAccounts = getSampleSavingsList();
        when(savingsAccountRepository.findSavingsAccountByCustomerIdentityNumber(any())).thenReturn(Optional.empty());
        assertNull(underTest.getSavingsAccountByIdentityNumber(any()));
    }

    @Test
    void WhenCallDeleteSavingsAccountById_ItShouldDeleteSavingsAccount(){
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId(1L);
        savingsAccount.setAccountBalance(0.0);
        when(savingsAccountRepository.findById(1L)).thenReturn(Optional.of(savingsAccount));
        doNothing().when(savingsAccountRepository).deleteById(1L);
        underTest.deleteSavingsAccountById(1L);
        verify(savingsAccountRepository,times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenThereIsNoSavingsAccountBeforeDeleteSavingsAccountById(){
        when(savingsAccountRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> underTest.deleteSavingsAccountById(1L));
    }


    private List<SavingsAccount> getSampleSavingsList(){
        List<SavingsAccount> savingsAccountList = new ArrayList<>();
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setCustomerIdentityNumber(123L);
        SavingsAccount savingsAccount1 = new SavingsAccount();
        savingsAccount1.setCustomerIdentityNumber(123L);
        savingsAccountList.add(savingsAccount);
        savingsAccountList.add(savingsAccount1);
        return savingsAccountList;
    }

}
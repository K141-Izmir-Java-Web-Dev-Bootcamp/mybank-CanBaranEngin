package org.kodluyoruz.mybank.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.DebitCard;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
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

@ExtendWith(MockitoExtension.class)
class DepositAccountServiceTest {

    @Mock
    public DepositAccountRepository depositAccountRepository;

    @Mock
    public ModelMapper modelMapper;

    @Mock
    public CustomerService customerService;

    @InjectMocks
    public DepositAccountService depositAccountService;

    @Test
    void create() {



    }

    @Test
    void getAll() throws JsonProcessingException {
        //init
        List<DepositAccount> expectedDepositAccountList = getSampleDepositAccountList();
        String expectedDepositAccountListJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedDepositAccountList);
        //when
        Mockito.when(depositAccountRepository.findAll()).thenReturn(expectedDepositAccountList);
        //then
        List<DepositAccount> actualDepositAccountList = depositAccountService.getAll();
        String actualDepositAccountListJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualDepositAccountList);
        //validate
        Assertions.assertEquals(expectedDepositAccountListJSON,actualDepositAccountListJSON);
        Mockito.verify(depositAccountRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getDepositAccountById_SUCCESS() throws JsonProcessingException {
        //init
        DepositAccount expectedDepositAccount = getSampleDepositAccountList().get(0);
        Optional<DepositAccount> expectedDepositAccountOpt = Optional.of(expectedDepositAccount);
        String expectedDepositAccountJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedDepositAccount);
        //when
        Mockito.when(depositAccountRepository.findById(1L)).thenReturn(expectedDepositAccountOpt);
        //then
        Optional<DepositAccount> actualDepositAccountOpt = depositAccountRepository.findById(1L);
        String actualDepositAccountOptJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualDepositAccountOpt.get());
        //validate
        Assertions.assertEquals(expectedDepositAccountJSON ,actualDepositAccountOptJSON);
        Mockito.verify(depositAccountRepository, Mockito.times(1)).findById(1L);
    }
    @Test
    void getDepositAccountById_NOT_SUCCESS() throws JsonProcessingException {
        //init
        DepositAccount expectedDepositAccount = getSampleDepositAccountList().get(0);
        Optional<DepositAccount> expectedDepositAccountOpt = Optional.of(expectedDepositAccount);
        String expectedDepositAccountJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedDepositAccount);
        //when
        Mockito.when(depositAccountRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        //then
        //validate
        Assertions.assertThrows(EntityNotFoundException.class,()->depositAccountService.getDepositAccountById(1L));
        Mockito.verify(depositAccountRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void getDepositAccountByIban() throws JsonProcessingException {
        //init
        DepositAccount expectedDepositAccount = getSampleDepositAccountList().get(0);
        Optional<DepositAccount> expectedDepositAccountOpt = Optional.of(expectedDepositAccount);
        String expectedDepositAccountJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedDepositAccount);
        //When
        Mockito.when(depositAccountRepository.findDepositAccountByIban(423423L)).thenReturn(expectedDepositAccountOpt);
        //Then
        Optional<DepositAccount> actualDepositAccountOpt = depositAccountRepository.findDepositAccountByIban(423423L);
        String actualDepositAccountOptJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualDepositAccountOpt.get());
        //validate
        Assertions.assertEquals(expectedDepositAccountJSON,actualDepositAccountOptJSON);
        Mockito.verify(depositAccountRepository, Mockito.times(1)).findDepositAccountByIban(423423L);

    }

    @Test
    void getDepositAccountByIdentityNumber() throws JsonProcessingException {
        //init
        List<DepositAccount> expectedDepositAccountList = getSampleDepositAccountList();
        Optional<List<DepositAccount>> expectedDepositAccountListOpt = Optional.of(expectedDepositAccountList);
        String expectedDepositAccountListJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedDepositAccountList);
        //When
        Mockito.when(depositAccountRepository.findDepositAccountByCustomerIdentityNumber(4535345L)).thenReturn(expectedDepositAccountListOpt);
        //Then
        Optional<List<DepositAccount>> actualDepositAccountList = depositAccountRepository.findDepositAccountByCustomerIdentityNumber(4535345L);
        String actualDepositAccountListJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualDepositAccountList);
        //Validate
        Assertions.assertEquals(expectedDepositAccountListJSON,actualDepositAccountListJSON);
    }

    private List<DepositAccount> getSampleDepositAccountList(){
        List<DepositAccount> userList = new ArrayList<>();
        DepositAccount depositAccount = new DepositAccount(1L,354223L,4535345L,new Customer(),new DebitCard(),new CreditCard());
        DepositAccount depositAccount2 = new DepositAccount(2L,35454534L,4535345L,new Customer(),new DebitCard(),new CreditCard());
        DepositAccount depositAccount3 = new DepositAccount(3L,7867867L,4535345L,new Customer(),new DebitCard(),new CreditCard());
        userList.add(depositAccount);
        userList.add(depositAccount2);
        userList.add(depositAccount3);
        return userList;
    }
}
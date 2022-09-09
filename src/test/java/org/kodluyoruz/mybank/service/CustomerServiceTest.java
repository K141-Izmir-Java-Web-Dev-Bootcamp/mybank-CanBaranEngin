package org.kodluyoruz.mybank.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jdk.jfr.Enabled;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.SavingsAccount;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;
import org.kodluyoruz.mybank.repository.CustomerRepository;
import org.kodluyoruz.mybank.repository.DepositAccountRepository;
import org.kodluyoruz.mybank.repository.SavingsAccountRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private SavingsAccountRepository savingsAccountRepository;
    @Mock
    private DepositAccountRepository depositAccountRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CustomerService underTest;


    @Test
    void whenCreateCustomerCalledWithValidRequest_ItShouldReturnValidCustomer() {
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();
        when(modelMapper.map(customerDto,Customer.class)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer result = underTest.create(customerDto);
        assertEquals(result,customer);
    }

    @Test
    void
    WhenCallCustomersById_ItShouldReturnCustomer() {
        Customer customer =getSampleCustomerList().get(0);
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        assertEquals(customer,underTest.getCustomerById(1L));


    }

    @Test
    void
    shouldThrowEntityNotFoundExceptionWhenThereIsNoCustomerBeforeGetCustomerById() {
        Customer customer =getSampleCustomerList().get(0);
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> underTest.getCustomerById(1L));


    }

    @Test
    void WhenCallGetCustomers_ItShouldReturnAllCustomers() {
        List<Customer> customerList = getSampleCustomerList();
        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> actualCustomerList=underTest.getCustomers();
        assertEquals(customerList,actualCustomerList);

    }
    @Test
    void ShouldThrowEntityNotFoundExceptionWhenThereIsNoCustomerBeforeGetCustomers() {
        List<Customer> customerList = getSampleCustomerList();
        customerList.clear();
        when(customerRepository.findAll()).thenReturn(customerList);
        assertThrows(EntityNotFoundException.class, () -> underTest.getCustomers());
    }

    @Test
    void WhenUpdateCustomerCalledWithValidRequest_ItShouldUpdateCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        CustomerDto customerDto = new CustomerDto();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(modelMapper.map(customer,CustomerDto.class)).thenReturn(customerDto);
        CustomerDto result = underTest.updateCustomer(1L,customerDto);
        assertEquals(customerDto,result);
        verify(customerRepository).findById(1L);
        verify(customerRepository).save(customer);
        verify(modelMapper).map(customer,CustomerDto.class);
    }

    @Test
    void ShouldThrowEntityNotFoundExceptionWhenThereIsNoCustomerBeforeUpdateCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        CustomerDto customerDto = new CustomerDto();
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> underTest.updateCustomer(1L,customerDto));


    }

    @Test
    void WhenDeleteCustomerCalledWithCustomerId_ItShouldDeleteCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setIdentityNumber(123L);
        List<DepositAccount> depositAccountList = getSampleDepositList();
        List<SavingsAccount> savingsAccountList = getSampleSavingsList();
        savingsAccountList.clear();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(depositAccountRepository.findDepositAccountByCustomerIdentityNumber(123L)).thenReturn(Optional.of(depositAccountList));
        when(savingsAccountRepository.findSavingsAccountByCustomerIdentityNumber(customer.getIdentityNumber())).thenReturn(Optional.of(savingsAccountList));
        assertEquals(true,underTest.deleteCustomer(1L));


    }


    private List<Customer> getSampleCustomerList(){
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        Customer customer1 = new Customer();
        customerList.add(customer);
        customerList.add(customer1);
        return customerList;
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
package org.kodluyoruz.mybank.service;

import jdk.jfr.Enabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;
import org.kodluyoruz.mybank.repository.CustomerRepository;
import org.kodluyoruz.mybank.repository.DepositAccountRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private DepositAccountService depositAccountService;
    @Mock
    private SavingsAccountService savingsAccountService;
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
    shouldThrowEntityNotFoundExceptionWhenThereIsNoCustomerBeforeGetCustomerById() {
        Customer customer =getSampleCustomerList().get(0);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
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
        when(customerRepository.findAll()).thenThrow(EntityNotFoundException.class);
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
    void deleteCustomer() {
    }


    private List<Customer> getSampleCustomerList(){
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        Customer customer1 = new Customer();
        customerList.add(customer);
        customerList.add(customer1);
        return customerList;
    }

}
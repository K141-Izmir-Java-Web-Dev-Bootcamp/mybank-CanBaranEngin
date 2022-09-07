package org.kodluyoruz.mybank.service;

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
    void shouldThrowEntityNotFoundExceptionWhenThereIsNoCustomerBeforeGetCustomerById() {
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
    void updateCustomer() {
        Customer expectedCustomer = getSampleCustomerList().get(0);
        CustomerDto customerDto = new CustomerDto("Can","Baran","canbaran", LocalDate.now(),1232L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(expectedCustomer));


    }

    @Test
    void deleteCustomer() {
    }


    private List<Customer> getSampleCustomerList(){
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer(1L,1232L,"Can","Baran","canbaran");
        Customer customer1 = new Customer(2L,674645L,"engin","can","engincan");
        customerList.add(customer);
        customerList.add(customer1);
        return customerList;
    }

}
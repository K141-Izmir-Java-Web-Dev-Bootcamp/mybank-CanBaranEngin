package org.kodluyoruz.mybank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.DebitCard;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;
import org.kodluyoruz.mybank.model.entity.dto.DebitCardDto;
import org.kodluyoruz.mybank.repository.DebitCardRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class DebitCardServiceTest {

    @Mock
    private DebitCardRepository debitCardRepository;
    @Mock
    private DepositAccountService depositAccountService;
    @Mock
    private CustomerService customerService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private DebitCardService underTest;



    @Test
    void whenCreateDepositCardCalledWithValidRequest_ItShouldReturnValidDebitCard() {
        Customer customer = new Customer();
        customer.setFirstName("can");
        customer.setLastName("baran");
        DepositAccount depositAccount = new DepositAccount();
        DebitCard debitCard = new DebitCard();
        DebitCardDto debitCardDto = new DebitCardDto();
        debitCardDto.setAccountId(1L);
        debitCardDto.setCustomerId(1L);
        when(modelMapper.map(debitCardDto,DebitCard.class)).thenReturn(debitCard);
        when(customerService.getCustomerById(debitCardDto.getCustomerId())).thenReturn(customer);
        when(depositAccountService.getDepositAccountById(debitCardDto.getAccountId())).thenReturn(depositAccount);
        when(debitCardRepository.save(debitCard)).thenReturn(debitCard);
        assertEquals(debitCard,underTest.create(debitCardDto));

    }

    @Test
    void WhenCallGetDebitCards_ItShouldReturnAllDebitCards() {
        List<DebitCard> debitCards = getSampleDebitCardList();
        when(debitCardRepository.findAll()).thenReturn(debitCards);
        assertEquals(debitCards,underTest.getAll());


    }
    @Test
    void WhenCallGetDebitCardsById_ItShouldReturnDebitCard() {
        DebitCard debitCard= new DebitCard();
        when(debitCardRepository.findById(1L)).thenReturn(Optional.of(debitCard));
        assertEquals(debitCard,underTest.getDebitCardById(1L));
    }

    @Test
    void ShouldThrowEntityNotFoundExceptionWhenThereIsNoDebitCardBeforeGetDebitCardById() {
        DebitCard debitCard= new DebitCard();
        when(debitCardRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> underTest.getDebitCardById(1L));


    }


    @Test
    void WhenCallDeleteDebitCardById_ItShouldDeleteDebitCards(){
        DebitCard debitCard = new DebitCard();
        debitCard.setId(1L);
        when(debitCardRepository.findById(1L)).thenReturn(Optional.of(debitCard));
        doNothing().when(debitCardRepository).deleteById(1L);
        underTest.deleteDebitCardById(1L);
        verify(debitCardRepository,times(1)).deleteById(1L);
    }

    @Test
    void ShouldThrowEntityNotFoundExceptionWhenThereIsNoDebitCardBeforeDeleteDebitCardById(){
        when(debitCardRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> underTest.deleteDebitCardById(1L));
    }




    private List<DebitCard> getSampleDebitCardList(){
        List<DebitCard> debitCardList = new ArrayList<>();
        DebitCard debitCard = new DebitCard();
        debitCard.setId(1L);
        DebitCard debitCard1 = new DebitCard();
        debitCard1.setId(1L);

        debitCardList.add(debitCard);
        debitCardList.add(debitCard1);
        return debitCardList;
    }


}
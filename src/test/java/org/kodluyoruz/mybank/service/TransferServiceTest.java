package org.kodluyoruz.mybank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kodluyoruz.mybank.exception.EntityNotFoundException;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.SavingsAccount;
import org.kodluyoruz.mybank.model.entity.Shopping;
import org.kodluyoruz.mybank.model.entity.Transfer;
import org.kodluyoruz.mybank.model.entity.dto.TransferDto;
import org.kodluyoruz.mybank.repository.DepositAccountRepository;
import org.kodluyoruz.mybank.repository.SavingsAccountRepository;
import org.kodluyoruz.mybank.repository.TransferRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private TransferRepository transferRepository;
    @Mock
    private DepositAccountService depositAccountService;
    @Mock
    private DepositAccountRepository depositAccountRepository;
    @Mock
    private SavingsAccountService savingsAccountService;
    @Mock
    private SavingsAccountRepository savingsAccountRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private TransferService underTest;



    @Test
    void WhenCreateTransferCalledWithValidRequestAndIfSelectDepositAccount_TransferProcessShouldSuccess() {
        Transfer transfer =new Transfer();
        transfer.setReceiverIBAN(1234L);
        transfer.setSenderIBAN(2345L);
        TransferDto transferDto = new TransferDto();
        transferDto.setMoneyValue(100.0);
        transferDto.setSenderIBAN(2345L);
        transferDto.setReceiverIBAN(1234L);
        DepositAccount depositAccountRec = new DepositAccount();
        depositAccountRec.setIban(1234L);
        depositAccountRec.setAccountBalance(100.0);
        DepositAccount depositAccountSend = new DepositAccount();
        depositAccountSend.setIban(2345L);
        depositAccountSend.setAccountBalance(200.0);
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setIban(1234L);

        when(modelMapper.map(transferDto,Transfer.class)).thenReturn(transfer);
        when(depositAccountService.getDepositAccountByIban(1234L)).thenReturn(depositAccountRec);
        when(depositAccountService.getDepositAccountByIban(2345L)).thenReturn(depositAccountSend);
        when(depositAccountRepository.save(depositAccountRec)).thenReturn(depositAccountRec);
        when(depositAccountRepository.save(depositAccountSend)).thenReturn(depositAccountSend);
        when(transferRepository.save(transfer)).thenReturn(transfer);
        assertEquals(true,underTest.create(transferDto));

    }
    @Test
    void WhenCreateTransferCalledWithValidRequestAndIfSelectSavingsAccountForSender_TransferProcessShouldNotSuccess() {
        Transfer transfer =new Transfer();
        transfer.setReceiverIBAN(1234L);
        transfer.setSenderIBAN(2345L);
        TransferDto transferDto = new TransferDto();
        transferDto.setMoneyValue(100.0);
        transferDto.setSenderIBAN(2345L);
        transferDto.setReceiverIBAN(1234L);
        DepositAccount depositAccountRec = new DepositAccount();
        depositAccountRec.setIban(3456L);
        depositAccountRec.setAccountBalance(100.0);
        DepositAccount depositAccountSend = new DepositAccount();
        depositAccountSend.setIban(2345L);
        depositAccountSend.setAccountBalance(200.0);
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setIban(1234L);
        when(modelMapper.map(transferDto,Transfer.class)).thenReturn(transfer);
        when(depositAccountService.getDepositAccountByIban(1234L)).thenReturn(depositAccountRec);
        when(depositAccountService.getDepositAccountByIban(2345L)).thenReturn(null);
        assertEquals(false,underTest.create(transferDto));

    }

    @Test
    void WhenCreateTransferCalledWithValidRequestAndIfSelectForReceiverAccountSavingsAnd_TransferProcessShouldSuccess() {
        Transfer transfer =new Transfer();
        transfer.setReceiverIBAN(1234L);
        transfer.setSenderIBAN(2345L);
        TransferDto transferDto = new TransferDto();
        transferDto.setMoneyValue(100.0);
        transferDto.setSenderIBAN(2345L);
        transferDto.setReceiverIBAN(3456L);
        DepositAccount depositAccountRec = new DepositAccount();
        depositAccountRec.setIban(1234L);
        depositAccountRec.setAccountBalance(100.0);
        DepositAccount depositAccountSend = new DepositAccount();
        depositAccountSend.setIban(2345L);
        depositAccountSend.setAccountBalance(200.0);
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setIban(1234L);
        savingsAccount.setAccountBalance(200.0);

        when(modelMapper.map(transferDto,Transfer.class)).thenReturn(transfer);
        when(depositAccountService.getDepositAccountByIban(2345L)).thenReturn(depositAccountSend);
        when(depositAccountService.getDepositAccountByIban(1234L)).thenReturn(null);
        when(savingsAccountService.getSavingsAccountByIban(1234L)).thenReturn(savingsAccount);
        when(savingsAccountRepository.save(savingsAccount)).thenReturn(savingsAccount);
        when(depositAccountRepository.save(depositAccountSend)).thenReturn(depositAccountSend);
        when(transferRepository.save(transfer)).thenReturn(transfer);
        assertEquals(true,underTest.create(transferDto));


    }

    @Test
    void WhenCallGetTransferById_ItShouldReturnTransfer() {
        Transfer transfer =new Transfer();
        when(transferRepository.findById(1L)).thenReturn(Optional.of(transfer));
        assertEquals(transfer,underTest.getTransferById(1L));
    }

    @Test
    void ShouldThrowEntityNotFoundExceptionWhenThereIsNoCreditCardBeforeGetCreditCardById() {
        Transfer transfer =new Transfer();
        when(transferRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> underTest.getTransferById(1L));
    }


}
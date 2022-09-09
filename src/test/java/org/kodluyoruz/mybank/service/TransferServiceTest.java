package org.kodluyoruz.mybank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.SavingsAccount;
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
    void WhenCreateTransferCalledWithValidRequestAndIfSelectDepositAccountAndGivenIbanTrue_TransferProcessShouldSuccess() {
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
       // when(savingsAccountService.getSavingsAccountByIban(567L)).thenReturn(savingsAccount);
        when(depositAccountRepository.save(depositAccountRec)).thenReturn(depositAccountRec);
        when(depositAccountRepository.save(depositAccountSend)).thenReturn(depositAccountSend);
        when(transferRepository.save(transfer)).thenReturn(transfer);
        underTest.create(transferDto);

    }

    @Test
    void getTransferById() {
    }
}
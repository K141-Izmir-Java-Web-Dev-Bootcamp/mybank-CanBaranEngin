package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.SavingsAccount;
import org.kodluyoruz.mybank.model.entity.Transfer;
import org.kodluyoruz.mybank.model.entity.dto.TransferDto;
import org.kodluyoruz.mybank.repository.DepositAccountRepository;
import org.kodluyoruz.mybank.repository.SavingsAccountRepository;
import org.kodluyoruz.mybank.repository.TransferRepository;
import org.kodluyoruz.mybank.service.impl.TrasnferServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransferService implements TrasnferServiceImpl {

    private final TransferRepository transferRepository;
    private final DepositAccountService depositAccountService;
    private final DepositAccountRepository depositAccountRepository;

    private final SavingsAccountService savingsAccountService;
    private final SavingsAccountRepository savingsAccountRepository;
            ;
    private final ModelMapper modelMapper;

    public TransferService(TransferRepository transferRepository, DepositAccountService depositAccountService, DepositAccountRepository depositAccountRepository, SavingsAccountService savingsAccountService, SavingsAccountRepository savingsAccountRepository, ModelMapper modelMapper) {
        this.transferRepository = transferRepository;
        this.depositAccountService = depositAccountService;
        this.depositAccountRepository = depositAccountRepository;
        this.savingsAccountService = savingsAccountService;
        this.savingsAccountRepository = savingsAccountRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public void create(TransferDto transferDto) {
        Transfer transfer = modelMapper.map(transferDto,Transfer.class);
        DepositAccount depositAccountReceiver = depositAccountService.getDepositAccountByIban(transfer.getReceiverIBAN());
        DepositAccount depositAccountSender = depositAccountService.getDepositAccountByIban(transfer.getSenderIBAN());
        //SavingsAccount savingsAccountSender = savingsAccountService.getSavingsAccountByIban(transfer.getSenderIBAN());
        SavingsAccount savingsAccountReceiver = savingsAccountService.getSavingsAccountByIban(transfer.getReceiverIBAN());
        transfer.setTransferDate(LocalDate.now());

        if(depositAccountReceiver!=null){
            if (depositAccountReceiver.getIban().equals(transfer.getReceiverIBAN()) && depositAccountSender!=null ){
                depositAccountReceiver.setAccountBalance(depositAccountReceiver.getAccountBalance()+transferDto.getMoneyValue());
                depositAccountRepository.save(depositAccountReceiver);
                depositAccountSender.setAccountBalance(depositAccountSender.getAccountBalance()-transferDto.getMoneyValue());
                depositAccountRepository.save(depositAccountSender);
                transferRepository.save(transfer);
            }
        }

        else if(savingsAccountReceiver.getIban().equals(transfer.getReceiverIBAN()) && depositAccountSender!=null){
            savingsAccountReceiver.setAccountBalance(savingsAccountReceiver.getAccountBalance() + transferDto.getMoneyValue());
            savingsAccountRepository.save(savingsAccountReceiver);
            depositAccountSender.setAccountBalance(depositAccountSender.getAccountBalance()-transferDto.getMoneyValue());
            depositAccountRepository.save(depositAccountSender);
            transferRepository.save(transfer);
        }


    }
}

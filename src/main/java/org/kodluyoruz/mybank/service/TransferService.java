package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.exception.ArithmeticException;
import org.kodluyoruz.mybank.exception.EntityNotFoundException;
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
import org.springframework.web.bind.annotation.PathVariable;

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
    public Boolean create(TransferDto transferDto) {
        Transfer transfer = modelMapper.map(transferDto,Transfer.class);
        DepositAccount depositAccountReceiver = depositAccountService.getDepositAccountByIban(transfer.getReceiverIBAN());
        DepositAccount depositAccountSender = depositAccountService.getDepositAccountByIban(transfer.getSenderIBAN());
        SavingsAccount savingsAccountReceiver = savingsAccountService.getSavingsAccountByIban(transfer.getReceiverIBAN());
        transfer.setTransferDate(LocalDate.now());
        if(depositAccountReceiver!=null && depositAccountSender!=null){
            depositAccountReceiver.setAccountBalance(depositAccountReceiver.getAccountBalance()+transferDto.getMoneyValue());
            depositAccountRepository.save(depositAccountReceiver);
            depositAccountSender.setAccountBalance(depositAccountSender.getAccountBalance()-transferDto.getMoneyValue());
            depositAccountRepository.save(depositAccountSender);
            transferRepository.save(transfer);
            return true;
        }

        else if(depositAccountSender!=null){
            savingsAccountReceiver.setAccountBalance(savingsAccountReceiver.getAccountBalance() + transferDto.getMoneyValue());
            savingsAccountRepository.save(savingsAccountReceiver);
            depositAccountSender.setAccountBalance(depositAccountSender.getAccountBalance()-transferDto.getMoneyValue());
            depositAccountRepository.save(depositAccountSender);
            transferRepository.save(transfer);
            return true;
        }
        else {
            return false;
        }

    }
    @Override
    public Transfer getTransferById(Long id) {
        Optional<Transfer> transfer = transferRepository.findById(id);
        return transfer.orElseThrow(()-> new EntityNotFoundException("Transfer"));
    }
}

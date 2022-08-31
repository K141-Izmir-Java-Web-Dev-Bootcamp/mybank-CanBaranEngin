package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.Transfer;
import org.kodluyoruz.mybank.model.entity.dto.TransferDto;
import org.kodluyoruz.mybank.repository.DepositAccountRepository;
import org.kodluyoruz.mybank.repository.TransferRepository;
import org.kodluyoruz.mybank.service.impl.TrasnferServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransferService implements TrasnferServiceImpl {

    private final TransferRepository transferRepository;
    private final DepositAccountService depositAccountService;
    private final DepositAccountRepository depositAccountRepository;
            ;
    private final ModelMapper modelMapper;

    public TransferService(TransferRepository transferRepository, DepositAccountService depositAccountService, DepositAccountRepository depositAccountRepository, ModelMapper modelMapper) {
        this.transferRepository = transferRepository;
        this.depositAccountService = depositAccountService;
        this.depositAccountRepository = depositAccountRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public void create(TransferDto transferDto) {
        Transfer transfer = modelMapper.map(transferDto,Transfer.class);
        DepositAccount depositAccountReceiver = depositAccountService.getDepositAccountByIban(transfer.getReceiverIBAN());
        DepositAccount depositAccountSender = depositAccountService.getDepositAccountByIban(transfer.getSenderIBAN());

        transfer.setTransferDate(LocalDate.now());

        if (transfer.getReceiverIBAN() == depositAccountReceiver.getIban()){
           depositAccountReceiver.setAccountBalance(depositAccountReceiver.getAccountBalance()+transferDto.getMoneyValue());
           depositAccountRepository.save(depositAccountReceiver);
           depositAccountSender.setAccountBalance(depositAccountSender.getAccountBalance()-transferDto.getMoneyValue());
           depositAccountRepository.save(depositAccountSender);
           transferRepository.save(transfer);
        }


    }
}

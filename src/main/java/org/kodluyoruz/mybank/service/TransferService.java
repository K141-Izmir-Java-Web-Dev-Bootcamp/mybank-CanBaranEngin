package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.Transfer;
import org.kodluyoruz.mybank.model.entity.dto.TransferDto;
import org.kodluyoruz.mybank.repository.TransferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Date;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final DepositAccountService depositAccountService;
            ;
    private final ModelMapper modelMapper;

    public TransferService(TransferRepository transferRepository, DepositAccountService depositAccountService, ModelMapper modelMapper) {
        this.transferRepository = transferRepository;
        this.depositAccountService = depositAccountService;
        this.modelMapper = modelMapper;
    }

    public Transfer create(TransferDto transferDto) {
        Transfer transfer = modelMapper.map(transferDto,Transfer.class);
        DepositAccount depositAccountReceiver = depositAccountService.getDepositAccountByIban(transfer.getReceiverIBAN());
        DepositAccount depositAccountSender = depositAccountService.getDepositAccountByIban(transfer.getSenderIBAN());

        transfer.setTransferDate(LocalDate.now());

        if (transfer.getReceiverIBAN() == depositAccountReceiver.getIban()){
           depositAccountReceiver.setAccountBalance(depositAccountReceiver.getAccountBalance()+transferDto.getMoneyValue());
           depositAccountSender.setAccountBalance(depositAccountSender.getAccountBalance()-transferDto.getMoneyValue());
            return transferRepository.save(transfer);
        }

        return null;

    }
}

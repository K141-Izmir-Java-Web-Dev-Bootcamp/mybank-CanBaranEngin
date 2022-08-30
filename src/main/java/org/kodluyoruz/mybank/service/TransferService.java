package org.kodluyoruz.mybank.service;

import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.Transfer;
import org.kodluyoruz.mybank.model.entity.dto.TransferDto;
import org.kodluyoruz.mybank.repository.TransferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        DepositAccount depositAccount = depositAccountService.getDepositAccountByIban(transfer.getReceiverIBAN());

        if (transfer.getReceiverIBAN() == depositAccount.getIban()){
           depositAccount.setAccountBalance(depositAccount.getAccountBalance()+transferDto.getMoneyValue());

        }

        return transferRepository.save(transfer);
    }
}

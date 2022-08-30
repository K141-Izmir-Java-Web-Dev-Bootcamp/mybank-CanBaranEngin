package org.kodluyoruz.mybank.controller;

import org.kodluyoruz.mybank.model.entity.Transfer;
import org.kodluyoruz.mybank.model.entity.dto.TransferDto;
import org.kodluyoruz.mybank.service.CustomerService;
import org.kodluyoruz.mybank.service.DepositAccountService;
import org.kodluyoruz.mybank.service.TransferService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class TransferController {

    private final TransferService transferService;
    private final DepositAccountService depositAccountService;
    private final ModelMapper modelMapper;

    public TransferController(TransferService transferService, DepositAccountService depositAccountService, ModelMapper modelMapper) {
        this.transferService = transferService;
        this.depositAccountService=depositAccountService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/transfer")
    public ResponseEntity<Transfer> create(@RequestBody TransferDto transferDto){
        Transfer transfers = transferService.create(transferDto);

        return ResponseEntity.ok(transfers);

    }

}

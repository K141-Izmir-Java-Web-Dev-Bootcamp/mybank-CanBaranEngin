package org.kodluyoruz.mybank.controller;

import org.kodluyoruz.mybank.model.entity.Transfer;
import org.kodluyoruz.mybank.model.entity.dto.TransferDto;
import org.kodluyoruz.mybank.service.CustomerService;
import org.kodluyoruz.mybank.service.DepositAccountService;
import org.kodluyoruz.mybank.service.TransferService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity create(@RequestBody TransferDto transferDto){
        if(transferService.create(transferDto)){
            return ResponseEntity.status(HttpStatus.CREATED).body("Transferring process has been done successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shouldn't transfer money from savings account,process has been denied");
        }


    }

    @GetMapping("/transfers/{id}")
    public ResponseEntity getTransfersById(@PathVariable Long id){
        Transfer getTransfer = transferService.getTransferById(id);
        TransferDto transferDto=modelMapper.map(getTransfer,TransferDto.class);
        return ResponseEntity.ok(transferDto);

    }

}

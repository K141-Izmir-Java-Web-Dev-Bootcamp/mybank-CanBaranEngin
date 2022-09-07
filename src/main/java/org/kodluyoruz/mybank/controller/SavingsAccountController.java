package org.kodluyoruz.mybank.controller;

import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.SavingsAccount;
import org.kodluyoruz.mybank.model.entity.dto.AccountDto;
import org.kodluyoruz.mybank.service.DepositAccountService;
import org.kodluyoruz.mybank.service.SavingsAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SavingsAccountController {

    private final SavingsAccountService savingsAccountService;
    private final ModelMapper modelMapper;

    public SavingsAccountController(SavingsAccountService savingsAccountService, ModelMapper modelMapper) {
        this.savingsAccountService = savingsAccountService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("SavingsAccount")
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto accountDto) {
        SavingsAccount savingsAccount = savingsAccountService.create(accountDto);
        AccountDto createAccountDto = modelMapper.map(savingsAccount,AccountDto.class);
        createAccountDto.setCustomerId(accountDto.getCustomerId());
        return ResponseEntity.ok(createAccountDto);

    }

    @GetMapping("SavingsAccount")
    public ResponseEntity<List<SavingsAccount>> getAll(){
        List<SavingsAccount> savingsAccounts = savingsAccountService.getAll();
        return ResponseEntity.ok(savingsAccounts);
    }

    @GetMapping("SavingsAccount/{id}")
    public ResponseEntity<SavingsAccount> getDepositAccountById(@PathVariable("id") Long id){
        SavingsAccount savingsAccount = savingsAccountService.getSavingsAccountById(id);
        return ResponseEntity.ok(savingsAccount);
    }

    @DeleteMapping("SavingsAccount/{id}")
    public ResponseEntity deleteSavingsAccountById(@PathVariable("id") Long id){
        savingsAccountService.deleteSavingsAccountById(id);
        return ResponseEntity.status(HttpStatus.OK).body("SavingsAccount " + id + " deleted successfully.");

    }



}

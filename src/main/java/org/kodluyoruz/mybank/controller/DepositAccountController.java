package org.kodluyoruz.mybank.controller;

import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.dto.AccountDto;
import org.kodluyoruz.mybank.model.entity.dto.CreditCardDto;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;
import org.kodluyoruz.mybank.service.DepositAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class    DepositAccountController {

    private final DepositAccountService depositAccountService;
    private final ModelMapper modelMapper;

    public DepositAccountController(DepositAccountService depositAccountService, ModelMapper modelMapper) {
        this.depositAccountService = depositAccountService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("DepositAccounts")
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto accountDto) {
        DepositAccount depositAccount = depositAccountService.create(accountDto);
        AccountDto createAccountDto = modelMapper.map(depositAccount,AccountDto.class);
        createAccountDto.setCustomerId(accountDto.getCustomerId());
        return ResponseEntity.ok(createAccountDto);

    }

    @GetMapping("DepositAccounts")
    public ResponseEntity<List<DepositAccount>> getAll(){
        List<DepositAccount> depositAccounts = depositAccountService.getAll();

        return ResponseEntity.ok(depositAccounts);
    }

    @GetMapping("DepositAccount/{id}")
    public ResponseEntity<DepositAccount> getDepositAccountById(@PathVariable("id") Long id){
        DepositAccount depositAccount = depositAccountService.getDepositAccountById(id);
        //AccountDto accountDto = modelMapper.map(depositAccount,AccountDto.class);
        //accountDto.setCustomerId(depositAccount.getCustomer().getId());

        return ResponseEntity.ok(depositAccount);

    }



}

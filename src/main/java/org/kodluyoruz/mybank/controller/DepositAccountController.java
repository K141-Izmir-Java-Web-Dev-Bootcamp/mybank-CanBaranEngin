package org.kodluyoruz.mybank.controller;

import org.kodluyoruz.mybank.model.entity.Account;
import org.kodluyoruz.mybank.model.entity.DepositAccount;
import org.kodluyoruz.mybank.model.entity.dto.AccountDto;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;
import org.kodluyoruz.mybank.service.DepositAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class    DepositAccountController {

    private final DepositAccountService depositAccountService;

    public DepositAccountController(DepositAccountService depositAccountService) {
        this.depositAccountService = depositAccountService;
    }

    @PostMapping("accounts")
    public ResponseEntity<DepositAccount> create(@RequestBody AccountDto accountDto) {
        DepositAccount depositAccount = depositAccountService.create(accountDto);
        return ResponseEntity.ok(depositAccount);

    }

}

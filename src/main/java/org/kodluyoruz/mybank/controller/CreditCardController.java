package org.kodluyoruz.mybank.controller;

import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.dto.CreditCardDto;
import org.kodluyoruz.mybank.service.CreditCardService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class CreditCardController {

    private final ModelMapper modelMapper;
    private final CreditCardService creditCardService;


    public CreditCardController(ModelMapper modelMapper, CreditCardService creditCardService) {
        this.modelMapper = modelMapper;
        this.creditCardService = creditCardService;
    }

    @PutMapping("creditcard")

    public ResponseEntity<CreditCardDto> create(@RequestBody CreditCardDto creditCardDto){

        CreditCard creditCards = creditCardService.create(creditCardDto);
        CreditCardDto createCreditCardDto = modelMapper.map(creditCards,CreditCardDto.class);
        createCreditCardDto.setCustomerId(creditCardDto.getCustomerId());
        createCreditCardDto.setAccountId(creditCardDto.getAccountId());

        return ResponseEntity.ok(createCreditCardDto);

    }
}

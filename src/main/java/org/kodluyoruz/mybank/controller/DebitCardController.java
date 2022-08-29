package org.kodluyoruz.mybank.controller;

import org.kodluyoruz.mybank.model.entity.DebitCard;
import org.kodluyoruz.mybank.model.entity.dto.DebitCardDto;
import org.kodluyoruz.mybank.service.DebitCardService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class DebitCardController {

    private final DebitCardService debitCardService;
    private final ModelMapper modelMapper;


    public DebitCardController(DebitCardService debitCardService, ModelMapper modelMapper) {
        this.debitCardService = debitCardService;
        this.modelMapper = modelMapper;
    }

    @PutMapping("DebitCard")
    public ResponseEntity<DebitCardDto> create(@RequestBody DebitCardDto debitCardDto){
        DebitCard createDebitCard = debitCardService.create(debitCardDto);
        DebitCardDto createDebitCardDto = modelMapper.map(createDebitCard,DebitCardDto.class);
        createDebitCardDto.setCustomerId(debitCardDto.getCustomerId());
        createDebitCardDto.setAccountId(debitCardDto.getAccountId());
        return ResponseEntity.ok(createDebitCardDto);

    }
}

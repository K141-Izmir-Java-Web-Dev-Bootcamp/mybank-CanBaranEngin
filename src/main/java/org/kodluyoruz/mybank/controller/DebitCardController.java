package org.kodluyoruz.mybank.controller;

import org.kodluyoruz.mybank.model.entity.DebitCard;
import org.kodluyoruz.mybank.model.entity.dto.DebitCardDto;
import org.kodluyoruz.mybank.service.DebitCardService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class DebitCardController {

    private final DebitCardService debitCardService;
    private final ModelMapper modelMapper;


    public DebitCardController(DebitCardService debitCardService, ModelMapper modelMapper) {
        this.debitCardService = debitCardService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("DebitCard")
    public ResponseEntity<DebitCardDto> create(@RequestBody DebitCardDto debitCardDto){
        DebitCard createDebitCard = debitCardService.create(debitCardDto);
        DebitCardDto createDebitCardDto = modelMapper.map(createDebitCard,DebitCardDto.class);
        createDebitCardDto.setCustomerId(debitCardDto.getCustomerId());
        createDebitCardDto.setAccountId(debitCardDto.getAccountId());
        return ResponseEntity.ok(createDebitCardDto);

    }

    @GetMapping("DebitCards")

    public ResponseEntity<List<DebitCard>> getAll(){
        List<DebitCard> debitCardList = debitCardService.getAll();
        return ResponseEntity.ok(debitCardList);
    }

    @GetMapping("DebitCards/{id}")
    public ResponseEntity<DebitCard> getDebitCardById(@PathVariable Long id){
        DebitCard debitCard = debitCardService.getDebitCardById(id);
        return ResponseEntity.ok(debitCard);
    }

    @DeleteMapping("DebitCard/{id}")
    public ResponseEntity deleteDebitCardById(@PathVariable("id") Long id){
        debitCardService.deleteDebitCardById(id);
        return ResponseEntity.status(HttpStatus.OK).body("DebitCard " + id + " deleted successfully.");

    }
}

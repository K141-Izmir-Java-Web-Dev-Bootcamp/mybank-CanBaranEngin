package org.kodluyoruz.mybank.controller;

import org.kodluyoruz.mybank.model.entity.CreditCard;
import org.kodluyoruz.mybank.model.entity.dto.CreditCardDto;
import org.kodluyoruz.mybank.service.CreditCardService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class CreditCardController {

    private final ModelMapper modelMapper;
    private final CreditCardService creditCardService;


    public CreditCardController(ModelMapper modelMapper, CreditCardService creditCardService) {
        this.modelMapper = modelMapper;
        this.creditCardService = creditCardService;
    }

    @PostMapping("creditCard")

    public ResponseEntity<CreditCardDto> create(@RequestBody CreditCardDto creditCardDto){

        CreditCard creditCards = creditCardService.create(creditCardDto);
        CreditCardDto createCreditCardDto = modelMapper.map(creditCards,CreditCardDto.class);
        createCreditCardDto.setCustomerId(creditCardDto.getCustomerId());
        createCreditCardDto.setAccountId(creditCardDto.getAccountId());

        return ResponseEntity.ok(createCreditCardDto);

    }
    @GetMapping("creditCardById/{id}")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable Long id){
        CreditCard creditCard = creditCardService.getCreditCardbyId(id);
        return ResponseEntity.ok(creditCard);
    }

    @GetMapping("creditCardDebt/{id}")
    public ResponseEntity<Double> getDebtById(@PathVariable Long id){
        Double debt = creditCardService.getDebtById(id);
        return ResponseEntity.ok(debt);
    }

    @PutMapping("creditCardPayDebt/{id}")
    public ResponseEntity payDebt(@PathVariable Long id,Double moneyValue,String password){

        if(creditCardService.payDebt(id,moneyValue,password)){
        return ResponseEntity.status(HttpStatus.OK).body("Dept has been payed successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debt payment process was gotten error ");
    }

    @DeleteMapping("CreditCard/{id}")
    public ResponseEntity deleteCreditCardById(@PathVariable("id") Long id){
        creditCardService.deleteCreditCardById(id);
        return ResponseEntity.status(HttpStatus.OK).body("CreditCard " + id + " deleted successfully.");

    }
}

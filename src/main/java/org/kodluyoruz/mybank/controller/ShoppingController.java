package org.kodluyoruz.mybank.controller;

import org.kodluyoruz.mybank.model.entity.Shopping;
import org.kodluyoruz.mybank.model.entity.dto.ShoppingDto;
import org.kodluyoruz.mybank.service.ShoppingService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class ShoppingController {

    private final ShoppingService shoppingService;

    private final ModelMapper modelMapper;

    public ShoppingController(ShoppingService shoppingService, ModelMapper modelMapper) {
        this.shoppingService = shoppingService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/shopping")
    public ResponseEntity create(@RequestBody ShoppingDto shoppingDto){

        shoppingService.create(shoppingDto);


        return ResponseEntity.status(HttpStatus.CREATED).body("Shopping process has been done successfully");
    }

}

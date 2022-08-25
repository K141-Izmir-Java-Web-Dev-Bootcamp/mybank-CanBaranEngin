package org.kodluyoruz.mybank.controller;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;
import org.kodluyoruz.mybank.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("customers")

    public ResponseEntity<CustomerDto> create(@RequestBody CustomerDto customerDto){
        CustomerDto customers = customerService.create(customerDto);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("customers/")

    public ResponseEntity<List<CustomerDto>> getCustomers(){
        List<CustomerDto> getCustomers = customerService.getCustomers();
        return ResponseEntity.ok(getCustomers);
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable ("id") Long id){
        CustomerDto getCustomer = customerService.getCustomerById(id);
        return ResponseEntity.ok(getCustomer);

    }

    @PutMapping("customers/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable ("id") Long id,@RequestBody CustomerDto customerDto){
        CustomerDto updatedCustomer = customerService.updateCustomer(id,customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("customers/{id}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable("id") Long id){
        Boolean status = customerService.deleteCustomer(id);
        return ResponseEntity.ok(status);
    }
}

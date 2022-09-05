package org.kodluyoruz.mybank.controller;

import org.kodluyoruz.mybank.model.entity.Customer;
import org.kodluyoruz.mybank.model.entity.dto.CustomerDto;
import org.kodluyoruz.mybank.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
public class CustomerController {

    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("customers")

    public ResponseEntity<CustomerDto> create(@RequestBody CustomerDto customerDto){
        Customer customers = customerService.create(customerDto);
        CustomerDto customersDto = modelMapper.map(customers,CustomerDto.class);
        return ResponseEntity.ok(customersDto);
    }

    @GetMapping("customers")

    public ResponseEntity<List<CustomerDto>> getCustomers(){
        List<Customer> customers = customerService.getCustomers();
        return ResponseEntity.ok(customers.stream().map(customer -> modelMapper.map(customer,CustomerDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable ("id") Long id){
        Customer getCustomer = customerService.getCustomerById(id);
        CustomerDto getCustomerDto = modelMapper.map(getCustomer,CustomerDto.class);
        return ResponseEntity.ok(getCustomerDto);

    }

    @PutMapping("customers/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable ("id") Long id,@RequestBody CustomerDto customerDto){
        CustomerDto updatedCustomer = customerService.updateCustomer(id,customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("customers/{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body("Customer " + id + " deleted successfully.");
    }
}

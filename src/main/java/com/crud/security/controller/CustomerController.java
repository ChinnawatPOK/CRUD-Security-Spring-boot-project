package com.crud.security.controller;

import com.crud.security.model.Customer;
import com.crud.security.model.response.ResponseModel;
import com.crud.security.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/customers", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<Customer>> createCustomer(
            @RequestBody Customer request) {
        var responseModel = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @GetMapping(value = "/customers", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<List<Customer>>> getCustomers() {
        var response = customerService.retrieveAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<ResponseModel<Customer>> getCustomer(
            @PathVariable Integer id) {
        var responseModel = customerService.retrieveCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<ResponseModel<Void>> putCustomer(
            @PathVariable Integer id, @Valid @RequestBody Customer body) {
        var responseModel = customerService.updateCustomer(id, body);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<ResponseModel<Void>> deleteCustomer(
            @PathVariable Integer id) {
        var responseModel = customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }
}

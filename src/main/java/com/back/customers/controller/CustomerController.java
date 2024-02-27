package com.back.customers.controller;

import com.back.customers.controller.api.CustomersApiInterface;
import com.back.customers.dto.CustomerDto;
import com.back.customers.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/")
@AllArgsConstructor
public class CustomerController implements CustomersApiInterface {

    private CustomerService customerService;

    @GetMapping("getAllCustomers")
    public ResponseEntity<List<CustomerDto>> getAllOrders() {
        return ResponseEntity.ok(this.customerService.getAllCustomers());
    }

    @PostMapping("getCustomerById")
    public ResponseEntity<CustomerDto> getCustomerById(@RequestBody Long id) {
        return ResponseEntity.ok(this.customerService.getCustomerById(id));
    }

    @PostMapping("updateCustomer")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customer) {
        return ResponseEntity.ok(this.customerService.updateCustomer(customer));
    }

    @PostMapping("createCustomer")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customer) {
        return ResponseEntity.ok(this.customerService.createCustomer(customer));
    }





}

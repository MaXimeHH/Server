package com.back.customers.controller;

import com.back.customers.config.RabbitConfig;
import com.back.customers.controller.api.CustomersApiInterface;
import com.back.customers.dto.CustomerDto;
import com.back.customers.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/")
@AllArgsConstructor
public class CustomerController implements CustomersApiInterface {

    private CustomerService customerService;

    private RabbitTemplate rabbitTemplate;

    @GetMapping("getAllCustomers")
    public ResponseEntity<List<CustomerDto>> getAllOrders() {
        rabbitTemplate.convertAndSend(RabbitConfig.GET_ALL_CUSTOMERS_QUEUE, "getAllCustomers");
        return ResponseEntity.ok(this.customerService.getAllCustomers());
    }

    @PostMapping("getCustomerById")
    public ResponseEntity<CustomerDto> getCustomerById(@RequestBody Long id) {
        rabbitTemplate.convertAndSend(RabbitConfig.GET_CUSTOMER_BY_ID_QUEUE, id.toString());
        return ResponseEntity.ok(this.customerService.getCustomerById(id));
    }

    @PostMapping("updateCustomer")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customer) {
        rabbitTemplate.convertAndSend(RabbitConfig.UPDATE_CUSTOMER_QUEUE, customer);
        return ResponseEntity.ok(this.customerService.updateCustomer(customer));
    }

    @PostMapping("createCustomer")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customer) {
        rabbitTemplate.convertAndSend(RabbitConfig.CREATE_CUSTOMER_QUEUE, customer);
        return ResponseEntity.ok(this.customerService.createCustomer(customer));
    }





}

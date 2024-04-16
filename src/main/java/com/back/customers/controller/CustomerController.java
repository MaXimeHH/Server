package com.back.customers.controller;

import com.back.customers.config.RabbitConfig;
import com.back.customers.controller.api.CustomersApiInterface;
import com.back.customers.dto.CustomerDto;
import com.back.customers.exception.CustomException;
import com.back.customers.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.aspectj.bridge.IMessageHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/")
@AllArgsConstructor
public class CustomerController implements CustomersApiInterface {

    private CustomerService customerService;

    private RabbitTemplate rabbitTemplate;

    @GetMapping("getAllCustomers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.GET_ALL_CUSTOMERS_QUEUE, "getAllCustomers");
            List<CustomerDto> customers = this.customerService.getAllCustomers();
            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (DataAccessException dataAccessException) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getCustomerById/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.GET_CUSTOMER_BY_ID_QUEUE, id.toString());
            CustomerDto customer = this.customerService.getCustomerById(id);
            if (customer == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("updateCustomer")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customer) {
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.UPDATE_CUSTOMER_QUEUE, customer);
            CustomerDto updatedCustomer = this.customerService.updateCustomer(customer);
            if (updatedCustomer == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getCustomerDto(), HttpStatus.CONFLICT);
        } catch (EntityNotFoundException entityNotFoundException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("createCustomer")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customer) {
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.CREATE_CUSTOMER_QUEUE, customer);
            CustomerDto newCustomer = this.customerService.createCustomer(customer);
            if (newCustomer == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteCustomer/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.DELETE_CUSTOMER_QUEUE, id.toString());
            this.customerService.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException entityNotFoundException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
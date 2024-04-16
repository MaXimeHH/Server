package com.back.customers.controller;

import com.back.customers.config.RabbitConfig;
import com.back.customers.dto.CustomerDto;
import com.back.customers.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerControllerTest.class);

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    //CAS DES TESTS PASSANTS
    @Test
    void testGetAllCustomers() {
        List<CustomerDto> customers = Collections.singletonList(new CustomerDto());
        when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<CustomerDto>> response = customerController.getAllCustomers();

        logger.info("HTTP Status: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.getBody());

        verify(rabbitTemplate).convertAndSend(RabbitConfig.GET_ALL_CUSTOMERS_QUEUE, "getAllCustomers");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
    }

    @Test
    void testGetCustomerById() {
        Long id = 1L;
        CustomerDto customer = new CustomerDto();
        when(customerService.getCustomerById(id)).thenReturn(customer);

        ResponseEntity<CustomerDto> response = customerController.getCustomerById(id);

        logger.info("HTTP Status: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.getBody());

        verify(rabbitTemplate).convertAndSend(RabbitConfig.GET_CUSTOMER_BY_ID_QUEUE, id.toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testCreateCustomer() {
        CustomerDto customer = new CustomerDto();
        CustomerDto newCustomer = new CustomerDto();
        when(customerService.createCustomer(customer)).thenReturn(newCustomer);

        ResponseEntity<CustomerDto> response = customerController.createCustomer(customer);

        logger.info("HTTP Status: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.getBody());

        verify(rabbitTemplate).convertAndSend(RabbitConfig.CREATE_CUSTOMER_QUEUE, customer);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newCustomer, response.getBody());
    }

    @Test
    void testDeleteCustomer() {
        Long id = 1L;
        doNothing().when(customerService).deleteCustomer(id);

        ResponseEntity<Void> response = customerController.deleteCustomer(id);

        logger.info("HTTP Status: {}", response.getStatusCode());

        verify(rabbitTemplate).convertAndSend(RabbitConfig.DELETE_CUSTOMER_QUEUE, id.toString());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testUpdateCustomer() {
        CustomerDto customer = new CustomerDto();
        CustomerDto updatedCustomer = new CustomerDto();
        when(customerService.updateCustomer(customer)).thenReturn(updatedCustomer);

        ResponseEntity<CustomerDto> response = customerController.updateCustomer(customer);

        logger.info("HTTP Status: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.getBody());

        verify(rabbitTemplate).convertAndSend(RabbitConfig.UPDATE_CUSTOMER_QUEUE, customer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCustomer, response.getBody());
    }

    //CAS DES TESTS NON PASSANTS
    @Test
    void testUpdateCustomerFailure() {
        CustomerDto customerBeforeUpdate = new CustomerDto();
        customerBeforeUpdate.setId(1L);

        when(customerService.updateCustomer(customerBeforeUpdate)).thenReturn(null);

        CustomerDto result = customerController.updateCustomer(customerBeforeUpdate).getBody();

        assertNotEquals(customerBeforeUpdate, result, "Expected customer to be updated, but it was not");
        verify(rabbitTemplate, times(1)).convertAndSend(RabbitConfig.UPDATE_CUSTOMER_QUEUE, customerBeforeUpdate);
    }

    @Test
    void testDeleteCustomerFailure() {
        Long id = 1L;
        doThrow(new EntityNotFoundException()).when(customerService).deleteCustomer(id);

        assertThrows(EntityNotFoundException.class, () -> {
            customerController.deleteCustomer(id);
        });
    }
    @Test
    void testCreateCustomerFailure() {
        CustomerDto customer = new CustomerDto();
        when(customerService.createCustomer(customer)).thenReturn(null);

        ResponseEntity<CustomerDto> response = customerController.createCustomer(customer);

        logger.info("HTTP Status: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.getBody());

        verify(rabbitTemplate).convertAndSend(RabbitConfig.CREATE_CUSTOMER_QUEUE, customer);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllCustomersFailure() {
        when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        ResponseEntity<List<CustomerDto>> response = customerController.getAllCustomers();

        logger.info("HTTP Status: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.getBody());

        verify(rabbitTemplate).convertAndSend(RabbitConfig.GET_ALL_CUSTOMERS_QUEUE, "getAllCustomers");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetCustomerByIdFailure() {
        Long id = 1L;
        when(customerService.getCustomerById(id)).thenReturn(null);

        ResponseEntity<CustomerDto> response = customerController.getCustomerById(id);

        logger.info("HTTP Status: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.getBody());

        verify(rabbitTemplate).convertAndSend(RabbitConfig.GET_CUSTOMER_BY_ID_QUEUE, id.toString());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody(), "Expected body to be null, but it was not");
    }
}

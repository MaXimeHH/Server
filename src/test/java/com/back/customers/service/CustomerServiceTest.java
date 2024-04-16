package com.back.customers.service;
import com.back.customers.dto.CustomerDto;
import com.back.customers.mapper.CustomerMapper;
import com.back.customers.model.Customer;
import com.back.customers.repository.CustomerRepository;
import com.back.customers.services.impl.CustomersServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @InjectMocks
    private CustomersServiceImpl customersService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    //CAS DES TESTS PASSANTS
    @Test
    public void testGetAllCustomers() {
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));
        when(customerMapper.customerToCustomerDto(customer)).thenReturn(customerDto);

        assertEquals(Collections.singletonList(customerDto), customersService.getAllCustomers());
        verify(customerRepository, times(1)).findAll();
        verify(customerMapper, times(1)).customerToCustomerDto(customer);
    }

    @Test
    public void testGetCustomerById() {
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerMapper.customerToCustomerDto(customer)).thenReturn(customerDto);

        assertEquals(customerDto, customersService.getCustomerById(1L));
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerMapper, times(1)).customerToCustomerDto(customer);
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setProfiles(new ArrayList<>());
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);
        when(customerMapper.customerToCustomerDto(customer)).thenReturn(customerDto);

        assertEquals(customerDto, customersService.updateCustomer(customerDto));
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(1)).saveAndFlush(any(Customer.class));
        verify(customerMapper, times(1)).customerToCustomerDto(customer);
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer();
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        customersService.deleteCustomer(1L);
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setProfiles(new ArrayList<>());
        when(customerMapper.customerDtoToCustomer(any(CustomerDto.class))).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.customerToCustomerDto(customer)).thenReturn(customerDto);

        assertEquals(customerDto, customersService.createCustomer(customerDto));
        verify(customerMapper, times(1)).customerDtoToCustomer(any(CustomerDto.class));
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(customerMapper, times(1)).customerToCustomerDto(customer);
    }

    //CAS DES TESTS NON PASSANTS
    @Test
    public void testCreateCustomerFailure() {
        assertThrows(IllegalArgumentException.class, () -> {
            customersService.createCustomer(null);
        });

        verify(customerMapper, times(0)).customerDtoToCustomer(any(CustomerDto.class));
        verify(customerRepository, times(0)).save(any(Customer.class));
        verify(customerMapper, times(0)).customerToCustomerDto(any(Customer.class));
    }

    @Test
    public void testGetAllCustomersReturnsEmptyList() {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        List<CustomerDto> result = customersService.getAllCustomers();

        assertNotNull(result, "The result should not be null");
        assertTrue(result.isEmpty(), "The result list should be empty");
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testGetCustomerByIdFailure() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            customersService.getCustomerById(1L);
        });

        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerMapper, times(0)).customerToCustomerDto(any(Customer.class));
    }

    @Test
    public void testDeleteCustomerFailure() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            customersService.deleteCustomer(1L);
        });

        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(0)).delete(any(Customer.class));
    }

    @Test
    public void testUpdateCustomerFailure() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setProfiles(new ArrayList<>());
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            customersService.updateCustomer(customerDto);
        });

        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(0)).saveAndFlush(any(Customer.class));
        verify(customerMapper, times(0)).customerToCustomerDto(any(Customer.class));
    }
}

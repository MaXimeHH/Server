package com.back.customers.services;

import com.back.customers.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(Long id);

    CustomerDto updateCustomer(CustomerDto customer);

    void deleteCustomer(Long id);

    CustomerDto createCustomer(CustomerDto customer);


}

package com.back.payetonkawa.services;

import com.back.payetonkawa.dto.CustomerDto;
import com.payetonkafe.entity.model.Customer;
import java.util.List;

public interface CustomerService {

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(Long id);

    CustomerDto updateCustomer(CustomerDto customer);

    String deleteCustomer(Long id);

    CustomerDto createCustomer(CustomerDto customer);


}

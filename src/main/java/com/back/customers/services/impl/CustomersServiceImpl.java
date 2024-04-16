package com.back.customers.services.impl;

import com.back.customers.dto.CustomerDto;
import com.back.customers.mapper.CustomerMapper;
import com.back.customers.model.Customer;
import com.back.customers.repository.CustomerRepository;
import com.back.customers.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersServiceImpl implements CustomerService {

    private static final String CUSTOMER_NOT_FOUND_MSG = "Customer with id %d not found";

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomersServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return this.customerRepository.findAll().stream()
                .map(this.customerMapper::customerToCustomerDto)
                .toList();
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        return this.customerRepository.findById(id)
                .map(this.customerMapper::customerToCustomerDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format(CUSTOMER_NOT_FOUND_MSG, id)));
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customer) {
        Customer oldCustomer = this.customerRepository.findById(customer.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(CUSTOMER_NOT_FOUND_MSG, customer.getId())));

        oldCustomer.setAdresse(customer.getAdresse());
        oldCustomer.setVille(customer.getVille());
        oldCustomer.setProfiles(customer.getProfiles().stream().map(customerMapper::profileDtoToProfile).toList());
        oldCustomer.setCodePostal(customer.getCodePostal());

        this.customerRepository.saveAndFlush(oldCustomer);

        return this.customerMapper.customerToCustomerDto(oldCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(CUSTOMER_NOT_FOUND_MSG, id)));

        customerRepository.delete(customer);
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customer) {
        Customer newCustomer = this.customerMapper.customerDtoToCustomer(customer);

        newCustomer.setCodePostal(customer.getCodePostal());
        newCustomer.setProfiles(customer.getProfiles().stream().map(customerMapper::profileDtoToProfile).toList());
        newCustomer.setVille(customer.getVille());
        newCustomer.setAdresse(customer.getAdresse());

        this.customerRepository.save(newCustomer);

        return this.customerMapper.customerToCustomerDto(newCustomer);
    }
}
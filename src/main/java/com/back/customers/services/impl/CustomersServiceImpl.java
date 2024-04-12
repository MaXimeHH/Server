package com.back.customers.services.impl;

import com.back.customers.dto.CustomerDto;
import com.back.customers.mapper.CustomerMapper;
import com.back.customers.model.Customer;
import com.back.customers.model.Profile;
import com.back.customers.repository.CustomerRepository;
import com.back.customers.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CustomersServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper;

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<CustomerDto> result = new ArrayList<>();
        this.customerRepository.findAll().forEach(
                elt -> result.add(this.customerMapper.customerToCustomerDto(elt))
        );
        return result;
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        return this.customerMapper.customerToCustomerDto(this.customerRepository.findById(id).get());
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customer) {
        Customer oldCustomer = this.customerRepository.findById(customer.getId()).get();

        oldCustomer.setAdresse(customer.getAdresse());
        oldCustomer.setVille(customer.getVille());
        oldCustomer.setProfiles(customer.getProfiles().stream().map(customerMapper::profileDtoToProfile).collect(Collectors.toList()));
        oldCustomer.setCodePostal(customer.getCodePostal());

        this.customerRepository.saveAndFlush(oldCustomer);

        return this.customerMapper.customerToCustomerDto(oldCustomer);
    }

    @Override
    public String deleteCustomer(Long id) {
        this.customerRepository.deleteById(id);
        return "Le client à bien été suprimée.";
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customer) {
        Customer newCustomer = this.customerMapper.customerDtoToCustomer(customer);

        newCustomer.setCodePostal(customer.getCodePostal());
        newCustomer.setProfiles(customer.getProfiles().stream().map(customerMapper::profileDtoToProfile).collect(Collectors.toList()));
        newCustomer.setVille(customer.getVille());
        newCustomer.setAdresse(customer.getAdresse());

        this.customerRepository.save(newCustomer);

        return this.customerMapper.customerToCustomerDto(newCustomer);
    }
}
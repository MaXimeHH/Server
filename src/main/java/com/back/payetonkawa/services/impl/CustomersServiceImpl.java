package com.back.payetonkawa.services.impl;

import com.back.payetonkawa.dto.CustomerDto;
import com.back.payetonkawa.mapper.CustomerMapper;
import com.back.payetonkawa.model.Customer;
import com.back.payetonkawa.model.Order;
import com.back.payetonkawa.repository.CustomerRepository;
import com.back.payetonkawa.services.CustomerService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
        oldCustomer.setProfile(customer.getProfile());
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
        newCustomer.setProfile(customer.getProfile());
        newCustomer.setVille(customer.getVille());
        newCustomer.setAdresse(customer.getAdresse());

        this.customerRepository.save(newCustomer);

        return this.customerMapper.customerToCustomerDto(newCustomer);
    }
}
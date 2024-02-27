package com.back.payetonkawa.services.impl;

import com.back.payetonkawa.dto.CustomerDto;
import com.back.payetonkawa.mapper.CustomerMapper;
import com.payetonkafe.entity.model.Customer;
import com.payetonkafe.entity.model.Order;
import com.back.payetonkawa.repository.CustomerRepository;
import com.back.payetonkawa.services.CustomerService;
import com.payetonkafe.entity.model.Profile;
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
        this.customerRepository.getAllCustomers().forEach(
                elt -> result.add(this.customerMapper.customerToCustomerDto(elt))
        );
        return result;
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        return this.customerMapper.customerToCustomerDto(this.customerRepository.getCustomerbyId(id));
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customer) {
        Customer oldCustomer = this.customerRepository.getCustomerbyId(customer.getId());

        oldCustomer.setAdresse(customer.getAdresse());
        oldCustomer.setVille(customer.getVille());
        oldCustomer.setProfiles((List<Profile>) customer.getProfile());
        oldCustomer.setCodePostal(customer.getCodePostal());

        this.customerRepository.update(oldCustomer);

        return this.customerMapper.customerToCustomerDto(oldCustomer);
    }

    @Override
    public String deleteCustomer(Long id) {
        this.customerRepository.deleteCustomer(id);
        return "Le client à bien été suprimée.";
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customer) {
        Customer newCustomer = this.customerMapper.customerDtoToCustomer(customer);

        newCustomer.setCodePostal(customer.getCodePostal());
        newCustomer.setProfiles((List<Profile>) customer.getProfile());
        newCustomer.setVille(customer.getVille());
        newCustomer.setAdresse(customer.getAdresse());

        this.customerRepository.createCustomer(newCustomer);

        return this.customerMapper.customerToCustomerDto(newCustomer);
    }
}
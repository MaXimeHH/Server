package com.back.customers.repository;

import com.back.customers.model.Customer;
import java.util.List;

public interface CustomerRepository {

    public List<Customer> getAllCustomers();

    public Customer getCustomerbyId(Long id);

    public void updateCustomer(Customer customer);

    public void deleteCustomer(Long id);

    public void update(Customer customer);

    public void createCustomer(Customer customer);
}

package com.back.payetonkawa.repository;

import com.back.payetonkawa.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

package com.back.customers.exception;

import com.back.customers.dto.CustomerDto;

public class CustomException extends RuntimeException {
    private CustomerDto customerDto;

    public CustomException(String message, CustomerDto customerDto) {
        super(message);
        this.customerDto = customerDto;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }
}

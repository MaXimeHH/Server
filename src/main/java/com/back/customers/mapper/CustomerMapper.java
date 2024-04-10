package com.back.customers.mapper;

import com.back.customers.dto.CustomerDto;
import com.back.customers.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "profile", target = "profile")
    @Mapping(source = "adresse", target = "adresse")
    @Mapping(source = "codePostal", target = "codePostal")
    @Mapping(source = "ville", target = "ville")
    CustomerDto customerToCustomerDto(Customer customer);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "profile", target = "profile")
    @Mapping(source = "adresse", target = "adresse")
    @Mapping(source = "codePostal", target = "codePostal")
    @Mapping(source = "ville", target = "ville")
    Customer customerDtoToCustomer(CustomerDto customer);
}

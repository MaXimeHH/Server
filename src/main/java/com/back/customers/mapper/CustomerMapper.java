package com.back.customers.mapper;

import com.back.customers.dto.CustomerDto;
import com.back.customers.dto.ProfileDto;
import com.back.customers.model.Customer;
import com.back.customers.model.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDto customerDto);

    ProfileDto profileToProfileDto(Profile profile);

    Profile profileDtoToProfile(ProfileDto profileDto);
}
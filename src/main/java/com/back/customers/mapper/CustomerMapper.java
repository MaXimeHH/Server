package com.back.customers.mapper;

import com.back.customers.dto.CustomerDto;
import com.back.customers.dto.ProfileDto;
import com.back.customers.model.Customer;
import com.back.customers.model.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "profiles", target = "profiles")
    @Mapping(source = "adresse", target = "adresse")
    @Mapping(source = "codePostal", target = "codePostal")
    @Mapping(source = "ville", target = "ville")
    CustomerDto customerToCustomerDto(Customer customer);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "profiles", target = "profiles")
    @Mapping(source = "adresse", target = "adresse")
    @Mapping(source = "codePostal", target = "codePostal")
    @Mapping(source = "ville", target = "ville")
    Customer customerDtoToCustomer(CustomerDto customerDto);

    ProfileDto profileToProfileDto(Profile profile);

    Profile profileDtoToProfile(ProfileDto profileDto);
}
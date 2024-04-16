package com.back.customers.mapper;

import com.back.customers.dto.CustomerDto;
import com.back.customers.dto.ProfileDto;
import com.back.customers.model.Customer;
import com.back.customers.model.Profile;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-15T16:34:10+0200",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.2 (Homebrew)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerDto customerToCustomerDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDto customerDto = new CustomerDto();

        customerDto.setId( customer.getId() );
        customerDto.setProfiles( profileListToProfileDtoList( customer.getProfiles() ) );
        customerDto.setAdresse( customer.getAdresse() );
        customerDto.setCodePostal( customer.getCodePostal() );
        customerDto.setVille( customer.getVille() );

        return customerDto;
    }

    @Override
    public Customer customerDtoToCustomer(CustomerDto customerDto) {
        if ( customerDto == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setId( customerDto.getId() );
        customer.setProfiles( profileDtoListToProfileList( customerDto.getProfiles() ) );
        customer.setAdresse( customerDto.getAdresse() );
        customer.setCodePostal( customerDto.getCodePostal() );
        customer.setVille( customerDto.getVille() );

        return customer;
    }

    @Override
    public ProfileDto profileToProfileDto(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        ProfileDto profileDto = new ProfileDto();

        profileDto.setFirstName( profile.getFirstName() );
        profileDto.setLastName( profile.getLastName() );
        profileDto.setMail( profile.getMail() );
        profileDto.setPassword( profile.getPassword() );

        return profileDto;
    }

    @Override
    public Profile profileDtoToProfile(ProfileDto profileDto) {
        if ( profileDto == null ) {
            return null;
        }

        Profile profile = new Profile();

        profile.setFirstName( profileDto.getFirstName() );
        profile.setLastName( profileDto.getLastName() );
        profile.setMail( profileDto.getMail() );
        profile.setPassword( profileDto.getPassword() );

        return profile;
    }

    protected List<ProfileDto> profileListToProfileDtoList(List<Profile> list) {
        if ( list == null ) {
            return null;
        }

        List<ProfileDto> list1 = new ArrayList<ProfileDto>( list.size() );
        for ( Profile profile : list ) {
            list1.add( profileToProfileDto( profile ) );
        }

        return list1;
    }

    protected List<Profile> profileDtoListToProfileList(List<ProfileDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Profile> list1 = new ArrayList<Profile>( list.size() );
        for ( ProfileDto profileDto : list ) {
            list1.add( profileDtoToProfile( profileDto ) );
        }

        return list1;
    }
}

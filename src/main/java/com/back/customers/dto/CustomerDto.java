package com.back.customers.dto;

import com.back.customers.model.Profile;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CustomerDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5879734174796748081L;

    private Long id;

    private List<ProfileDto> profiles;

    private String adresse;

    private String codePostal;

    private String ville;

}
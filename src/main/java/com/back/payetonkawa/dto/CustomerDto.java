package com.back.payetonkawa.dto;

import com.payetonkafe.entity.model.Profile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {

    private Long id;

    private Profile profile;

    private String adresse;

    private String codePostal;

    private String ville;
}

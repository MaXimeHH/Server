package com.back.payetonkawa.dto;

import com.back.payetonkawa.model.Profile;
import jakarta.persistence.OneToOne;
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

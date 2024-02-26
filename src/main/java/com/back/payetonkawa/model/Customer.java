package com.back.payetonkawa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    private Long id;

    @OneToOne
    private Profile profile;

    private String adresse;

    private String codePostal;

    private String ville;
}

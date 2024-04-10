package com.back.customers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany
    private List<Profile> profiles;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "codePostal")
    private String codePostal;

    @Column(name = "ville")
    private String ville;

}

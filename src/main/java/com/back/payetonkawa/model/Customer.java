package com.back.payetonkawa.model;

import com.payetonkafe.entity.model.Profile;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Profile> profiles;

    private String adresse;

    private String codePostal;

    private String ville;
}

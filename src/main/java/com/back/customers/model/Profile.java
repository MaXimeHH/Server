package com.back.customers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Profile {
    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String mail;

    private String password;
}
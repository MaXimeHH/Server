package com.back.customers.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class ProfileDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6832180488656685124L;

    private String firstName;
    private String lastName;
    private String mail;
    private String password;
}
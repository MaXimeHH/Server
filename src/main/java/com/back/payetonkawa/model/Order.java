package com.back.payetonkawa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Order {
    @Id
    private Long id;

    @ManyToMany
    private List<Product> products;

    @ManyToOne
    private Customer customer;

}

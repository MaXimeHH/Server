package com.back.payetonkawa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Product {

    @Id
    private Long id;

    private LocalDateTime createdAt;

    private String name;

    private String details;

    private BigDecimal price;

    private String description;

    private String color;

    private Integer stock;
}

package com.greatminds.ayni.shopping.domain.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class Sale {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private Double unitPrice;

    @Getter
    private Long quantity;

    @Getter
    private String imageUrl;

    public Sale() {
    }

    public Sale(String name, String description, Double unitPrice, Long quantity, String imageUrl) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }
}

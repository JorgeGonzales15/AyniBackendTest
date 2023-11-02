package com.greatminds.ayni.management.domain.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class Product {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private String distance;

    @Getter
    private String depth;

    @Getter
    private String weather;

    @Getter
    private String groundType;

    @Getter
    private String season;

    @Getter
    private String imageUrl;


    public Product() {
    }

    public Product(String name, String description, String distance, String depth, String weather, String groundType, String season, String imageUrl) {
        this.name = name;
        this.description = description;
        this.distance = distance;
        this.depth = depth;
        this.weather = weather;
        this.groundType = groundType;
        this.season = season;
        this.imageUrl = imageUrl;
    }
}

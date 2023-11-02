package com.greatminds.ayni.management.domain.model.aggregates;

import com.greatminds.ayni.management.domain.model.entities.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Crop {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String name;

    @Getter
    private Boolean undergrowth;

    @Getter
    private Boolean fertilize;

    @Getter
    private Boolean oxygenate;

    @Getter
    private Boolean line;

    @Getter
    private Boolean hole;

    @Getter
    private Long watered;

    @Getter
    private Long pestCleaning;

    @Getter
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Crop() {
    }

    public Crop(String name, Boolean undergrowth, Boolean fertilize, Boolean oxygenate, Boolean line, Boolean hole, Long watered, Long pestCleaning, Product product) {
        this.name = name;
        this.undergrowth = undergrowth;
        this.fertilize = fertilize;
        this.oxygenate = oxygenate;
        this.line = line;
        this.hole = hole;
        this.watered = watered;
        this.pestCleaning = pestCleaning;
        this.product = product;
    }

}

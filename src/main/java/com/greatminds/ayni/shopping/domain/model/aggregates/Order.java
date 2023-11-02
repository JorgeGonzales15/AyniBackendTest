package com.greatminds.ayni.shopping.domain.model.aggregates;

import com.greatminds.ayni.shopping.domain.model.entities.Sale;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Getter
    private Long orderedBy;

    @Getter
    private Long acceptedBy;

    @Getter
    private String description;

    @Getter
    private Long quantity;

    @Getter
    private String status;

    @Getter
    private Date orderedDate;

    @Getter
    private Double totalPrice;

    @Getter
    private String paymentMethod;

    public Order(String description, Double totalPrice, Long quantity, String paymentMethod, String status, Sale sale, Long orderedBy, Long acceptedBy, Date orderedDate) {
        this.description = description;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.orderedBy = orderedBy;
        this.acceptedBy = acceptedBy;
        this.sale = sale;
    }

    public Order() {

    }

    public void updateDate(Date currentDate) {
        this.orderedDate = currentDate;
    }

}

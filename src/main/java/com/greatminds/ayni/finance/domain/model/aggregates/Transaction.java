package com.greatminds.ayni.finance.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Transaction extends AbstractAggregateRoot<Transaction> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String costName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String description;
    private String type;
    private Double price;

    private Integer quantity;


    public Transaction(String costName, String description, String type, Double price, Integer quantity) {
        this.costName = costName;
        this.description = description;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.date = new Date();
    }


    public Transaction() {

    }

    public void update(Transaction request) {
        if (request.costName != null) {
            this.costName = request.costName;
        }
        if (request.description != null) {
            this.description = request.description;
        }
        if (request.type != null) {
            this.type = request.type;
        }
        if (request.price != null) {
            this.price = request.price;
        }
        if (request.quantity != null) {
            this.quantity = request.quantity;
        }
    }

    public void updateDate(Date currentDate) {
        this.date = currentDate;
    }
}

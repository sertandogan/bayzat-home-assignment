package com.bayztracker.domain;


import com.bayztracker.enums.Status;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "alerts")
@SequenceGenerator(name = "seq_alert", sequenceName = "seq_alert")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_alert")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column
    private long userId;
    @Column
    private float targetPrice;

    @Column
    private Date createdAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;


    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public float getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(float targetPrice) {
        this.targetPrice = targetPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}

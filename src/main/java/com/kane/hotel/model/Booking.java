package com.kane.hotel.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Booking {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User booker;
    @ManyToOne(targetEntity = Ad.class, fetch = FetchType.LAZY)
    private Ad ad;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp createdAt;
    private double amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getBooker() {
        return booker;
    }

    public void setBooker(User booker) {
        this.booker = booker;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

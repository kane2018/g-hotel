package com.kane.hotel.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private int rating;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne(targetEntity = Ad.class, fetch = FetchType.LAZY, optional = false)
    private Ad ad;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    private User author;

    @PrePersist
    @PreUpdate
    public void onPrePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Timestamp(System.currentTimeMillis());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}

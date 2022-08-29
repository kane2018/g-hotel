package com.kane.hotel.model;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
public class Booking {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
    private User booker;
    @ManyToOne(targetEntity = Ad.class, fetch = FetchType.LAZY, optional = false)
    private Ad ad;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;
    private double amount;
    @Column(columnDefinition = "TEXT")
    private String comment;

    @PrePersist
    @PreUpdate
    public void onPrePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Timestamp(System.currentTimeMillis());
        }

        if (this.amount == 0) {
            this.amount = this.ad.getPrice() * this.getDuration();
        }
    }

    public long getDuration() {

        DateTime start = new DateTime(this.startDate);
        DateTime end = new DateTime(this.endDate);

        Duration duration = new Duration(start, end);

        return duration.getStandardDays();
    }

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isBookableDates() {

        List<Date> notAvailableDays = this.ad.getNotAvailableDays();

        List<Date> bookingDays = this.getDays();

        /*bookingDays.forEach((day) -> {
            if (notAvailableDays.contains(day)) {
                return false;
            }
        });*/

        for (Date day : bookingDays
        ) {
            if (notAvailableDays.contains(day)) {
                return false;
            }
        }

        return true;
    }

    public List<Date> getDays() {

        List<Date> days = new ArrayList<>();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this.getStartDate());

        while (calendar.getTime().before(this.getEndDate())) {
            Date result = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            days.add(result);
        }

        return days;
    }

}

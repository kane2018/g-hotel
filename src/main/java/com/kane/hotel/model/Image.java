package com.kane.hotel.model;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue
    private Integer id;
    private String url;
    private String caption;
    @ManyToOne(optional = false)
    @JoinColumn
    private Ad ad;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", caption='" + caption + '\'' +
                '}';
    }
}

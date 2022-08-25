package com.kane.hotel.model;

import com.github.slugify.Slugify;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    @Size(min = 10, message = "Le titre doit faire au moins 10 caractéres !")
    private String title;
    private String slug;
    private float price;
    @Column(columnDefinition = "TEXT")
    @Size(min = 20, message = "Votre introduction doit faire au moins 20 caractères.")
    private String introduction;
    @Column(columnDefinition = "TEXT")
    @Size(min = 100, message = "Votre description doit faire au moins 100 caractères.")
    private String content;
    @javax.validation.constraints.Pattern(
            regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)",
            message = "Veuillez donner une url valide pour l'image")
    private String coverImage;
    private int rooms;
    @OneToMany(mappedBy = "ad", targetEntity = Image.class, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Image> images;
    @ManyToOne(optional = false)
    @JoinColumn
    private User author;

    @PrePersist
    @PreUpdate
    public void onPrePersist() {
        if(this.slug == null) {
            Slugify s = Slugify.builder().build();
            this.slug = s.slugify(this.title);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public List<Image> getImages() {
        if(images == null) {
            images = new ArrayList<Image>();
        }
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}

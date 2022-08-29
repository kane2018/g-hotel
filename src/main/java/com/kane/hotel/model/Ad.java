package com.kane.hotel.model;

import com.github.slugify.Slugify;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

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
    @OneToMany(mappedBy = "ad")
    private List<Booking> bookings;
    @OneToMany(mappedBy = "ad", fetch = FetchType.LAZY)
    private List<Comment> comments;


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

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Date> getNotAvailableDays() {

        List<Date> notAvailableDays = new ArrayList<>();

        for (Booking booking: this.bookings
             ) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(booking.getStartDate());

            while (calendar.getTime().before(booking.getEndDate())) {
                Date result = calendar.getTime();
                calendar.add(Calendar.DATE, 1);
                notAvailableDays.add(result);
            }

        }

        return notAvailableDays;
    }

    public float getAvgRatings() {
        float somme = 0;
        for (Comment comment: this.comments
             ) {
            somme += comment.getRating();
        }

        if(this.comments.size() != 0) {
            return somme / this.comments.size();
        }

        return 0;
    }

    public Comment getCommentFromAuthor(User author) {

        for (Comment comment: this.getComments()
             ) {

            if (comment.getAuthor().getEmail().equals(author.getEmail())) {
                return comment;
            }
        }

        return null;
    }
}

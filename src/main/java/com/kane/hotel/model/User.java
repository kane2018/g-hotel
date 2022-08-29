package com.kane.hotel.model;

import com.github.slugify.Slugify;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/*@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "hash",
                fieldMatch = "passwordConfirm",
                message = "Les mots de passe ne correspondent pas!"
        )
})*/
@Entity
@NamedEntityGraph(name = "User.bookings", attributeNodes = @NamedAttributeNode("bookings"))
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String firstName;
    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String lastName;
    @Column(nullable = false, unique = true)
    @Email(message = "Veuillez renseignez un email valide")
    @NotEmpty
    private String email;
    @Column(nullable = true)
    @Pattern(
            regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)",
            message = "Veuillez donner une url valide pour l'image")
    @NotEmpty
    private String picture;
    @Column(nullable = false)
    //@ValidPassword
    private String hash;
    @Transient
    //@ValidPassword
    public String passwordConfirm;
    @Transient
    //@ValidPassword
    public String old;
    @Column(nullable = false)
    @Size(min = 20, message = "Votre introduction doit faire au moins 20 caractères.")
    private String introduction;
    @Column(columnDefinition = "TEXT", nullable = false)
    @Size(min = 100, message = "Votre description doit faire au moins 100 caractères.")
    private String description;
    @Column(nullable = false)
    private String slug;
    @OneToMany(mappedBy = "author", targetEntity = Ad.class, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Ad> ads;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;
    @OneToMany(mappedBy = "booker")
    private List<Booking> bookings;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Comment> comments;


    @PrePersist
    @PreUpdate
    public void onPrePersist() {
        if(this.slug == null) {
            Slugify s = Slugify.builder().build();
            this.slug = s.slugify(this.getFullName());
        }

    }

    public String getFullName() {
        return this.firstName+" "+this.lastName ;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    public List<Role> getRoles() {
        if(roles == null) {
            roles = new ArrayList<Role>();
        }
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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
}

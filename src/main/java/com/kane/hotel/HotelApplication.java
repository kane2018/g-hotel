package com.kane.hotel;

import com.kane.hotel.model.*;
import com.kane.hotel.service.*;
import io.bloco.faker.Faker;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class HotelApplication implements CommandLineRunner {

    @Autowired
    private AdService adService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private CommentService commentService;

    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*Faker f = new Faker("fr");

        List<User> users = new ArrayList<>();

        Role role = new Role();
        role.setName("ROLE_ADMIN");

        Role r = roleService.ajouterRole(role);

        String p = "https://randomuser.me/api/portraits/";

        String pId = f.number.between(1, 99)+".jpg";

        p +=  "men/"+pId;

        User u = new User();

        u.setFirstName("Makane");
        u.setLastName("KANE");
        u.setEmail("makane9305@gmail.com");
        u.setPicture(p);
        u.setIntroduction(f.lorem.sentence(20));
        String d = "";
        StringBuffer sbr = new StringBuffer();
        for (String text : f.lorem.paragraphs(5)
        ) {
            sbr.append("<p>" + text + "</p>");
        }
        d = sbr.toString();
        u.setDescription(d);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String h = bCryptPasswordEncoder.encode("$Ma2000@");
        u.passwordConfirm = h;
        u.setHash(h);
        u.getRoles().add(r);

        userService.ajouter(u);

        Genre[] genres = Genre.values();

        Role rU = new Role();
        rU.setName("ROLE_USER");
        roleService.ajouterRole(rU);

        for (int i = 1; i <= 10; i++) {
            User user = new User();

            Genre genre = genres[f.number.between(0,1)];

            String picture = "https://randomuser.me/api/portraits/";

            String pictureId = f.number.between(1, 99)+".jpg";

            picture += (genre.name() == "male") ? "men/"+pictureId : "women/"+pictureId;

            user.setFirstName(f.name.firstName());
            user.setLastName(f.name.lastName());
            user.setEmail(f.internet.email());
            user.setPicture(picture);
            user.setIntroduction(f.lorem.sentence(20));
            String description = "";
            StringBuffer sb = new StringBuffer();
            for (String text : f.lorem.paragraphs(5)
            ) {
                sb.append("<p>" + text + "</p>");
            }
            description = sb.toString();
            user.setDescription(description);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hash = passwordEncoder.encode("$Ma2000@");
            user.passwordConfirm = hash;
            user.setHash(hash);
            user.getRoles().add(rU);

            userService.ajouter(user);

            users.add(user);

        }

        for (int i = 1; i <= 30; i++) {
            Ad a = new Ad();
            String titre = f.lorem.sentence();
            a.setTitle(titre);
            a.setCoverImage(f.placeholdit.image("1000x400"));
            a.setIntroduction(f.lorem.paragraph(2));
            String content = "";
            StringBuffer sb = new StringBuffer();
            for (String text : f.lorem.paragraphs(5)
            ) {
                sb.append("<p>" + text + "</p>");
            }
            content = sb.toString();
            a.setContent(content);
            a.setPrice(f.number.between(80, 120));
            a.setRooms(f.number.between(2, 5));

            User user = users.get(f.number.between(0, users.size() - 1));

            a.setAuthor(user);

            adService.ajouter(a);

            for (int j = 1; j < f.number.between(0, 10); j++) {
                Booking booking = new Booking();

                DateTime t = new DateTime();

                DateTime sixMois =  t.minusMonths(6);
                DateTime troisMois =  t.minusMonths(3);

                booking.setCreatedAt(new Timestamp(sixMois.toDate().getTime()));
                booking.setStartDate(new Timestamp(troisMois.toDate().getTime()));

                int duration = f.number.between(3, 10);

                booking.setEndDate(new Timestamp(troisMois.plusDays(duration).toDate().getTime()));
                User booker = users.get(f.number.between(0, users.size() - 1));
                booking.setBooker(booker);
                booking.setAd(a);
                booking.setAmount(a.getPrice() * duration);
                booking.setComment(f.lorem.paragraph());

                bookingService.ajouter(booking);

                if(f.number.between(0, 1) != 0) {
                    Comment comment = new Comment();
                    comment.setContent(f.lorem.paragraph());
                    comment.setRating(f.number.between(1, 5));
                    comment.setAuthor(booker);
                    comment.setAd(a);

                    commentService.ajouter(comment);
                }
            }

            for(int j = 1; j <= f.number.between(2, 5); j++){
                Image image = new Image();

                image.setUrl(f.placeholdit.image());
                image.setCaption(f.lorem.sentence());
                image.setAd(a);

                imageService.ajouter(image);
            }
        }*/
    }
}

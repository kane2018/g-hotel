package com.kane.hotel.controller;


import com.kane.hotel.configuration.MyUserDetails;
import com.kane.hotel.model.Ad;
import com.kane.hotel.model.Booking;
import com.kane.hotel.model.Comment;
import com.kane.hotel.service.AdService;
import com.kane.hotel.service.BookingService;
import com.kane.hotel.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Date;

@Controller
public class BookingController {

    @Autowired
    private AdService adService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CommentService commentService;

    @RolesAllowed({"USER"})
    @GetMapping(value = "/ads/{slug}/book", name = "booking_create")
    public String book(@PathVariable("slug") String slug, Model model) {
        Booking booking = new Booking();
        Ad ad = adService.getAd(slug);
        model.addAttribute("booking", booking);
        model.addAttribute("ad", ad);
        return "booking/book";
    }

    @RolesAllowed({"USER"})
    @PostMapping(value = "ads/{slug}/book", name = "booking_save")
    public String bookSave(@PathVariable("slug") String slug, @Valid Booking booking, BindingResult bindingResult, Model model, RedirectAttributes attributes) {

        Ad ad = adService.getAd(slug);

        if(booking.getStartDate().before(new Date())) {
            bindingResult.rejectValue("startDate", null, "La date d'arrivée doit être " +
                    "ultérieure à la date d'aujourd'hui");
        }

        if(booking.getStartDate().getTime() >= booking.getEndDate().getTime()) {
            bindingResult.rejectValue("endDate", null, "La date de départ doit être " +
                    "supérieure à la date d'arrivée !");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("ad", ad);
            return "booking/book";
        }

        Object m = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUserDetails u = (MyUserDetails) m;

        booking.setBooker(u.getUser());
        booking.setAd(ad);

        if(!booking.isBookableDates()) {
            model.addAttribute("ad", ad);
            model.addAttribute("warning", "Les dates que vous avez choisi ne peuvent être réservées : elles sont déjà prises.");
            return "booking/book";
        } else {
            Booking boo = bookingService.ajouter(booking);

            attributes.addFlashAttribute("withAlert", true);

            return "redirect:/booking/"+boo.getId();
        }


    }

    @GetMapping(value = "/booking/{id}", name = "booking_show")
    public String show(@PathVariable("id") Integer id, Model model) {

        Object m = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUserDetails u = (MyUserDetails) m;

        Booking booking = bookingService.getBooking(id);
        Comment comment = new Comment();
        model.addAttribute("booking", booking);
        model.addAttribute("commentF", comment);
        model.addAttribute("author", u.getUser());

        return "booking/show";
    }

    @PostMapping(value = "/booking/{id}", name = "booking_show_comment")
    public String showComment(@PathVariable("id") Integer id, Model model, @Valid Comment comment, BindingResult result) {

        Object m = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUserDetails u = (MyUserDetails) m;

        Booking booking = bookingService.getBooking(id);
        model.addAttribute("booking", booking);
        model.addAttribute("commentF", comment);
        model.addAttribute("author", u.getUser());

        if(!result.hasErrors()) {

            comment.setAuthor(u.getUser());
            comment.setAd(booking.getAd());

            commentService.ajouter(comment);
            model.addAttribute("success", "Votre commentaire a été bien pris en compte !");
        }

        return "booking/show";
    }

    @GetMapping(value = "/account/bookings", name = "account_bookings")
    public String bookings(Model model) {

        Object m = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUserDetails u = (MyUserDetails) m;

        model.addAttribute("bookings", bookingService.mesBookings(u.getUser()));
        model.addAttribute("author", u.getUser());

        return "account/bookings";
    }
}

package com.kane.hotel.service;

import com.kane.hotel.model.Booking;
import com.kane.hotel.model.User;

import java.util.List;

public interface BookingService {
    Booking ajouter(Booking booking);

    Booking getBooking(Integer id);

    List<Booking> mesBookings(User user);
}

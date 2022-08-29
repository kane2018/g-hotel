package com.kane.hotel.service;

import com.kane.hotel.dao.BookingRepository;
import com.kane.hotel.model.Booking;
import com.kane.hotel.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements BookingService{

    @Autowired
    BookingRepository repository;

    @Override
    public Booking ajouter(Booking booking) {
        return repository.save(booking);
    }

    @Override
    public Booking getBooking(Integer id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Booking> mesBookings(User user) {
        return repository.findByUser(user);
    }
}

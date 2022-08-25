package com.kane.hotel.service;

import com.kane.hotel.dao.BookingRepository;
import com.kane.hotel.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService{

    @Autowired
    BookingRepository repository;

    @Override
    public Booking ajouter(Booking booking) {
        return repository.save(booking);
    }
}

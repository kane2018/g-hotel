package com.kane.hotel.dao;

import com.kane.hotel.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
}

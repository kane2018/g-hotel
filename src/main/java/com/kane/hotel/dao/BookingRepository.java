package com.kane.hotel.dao;

import com.kane.hotel.model.Booking;
import com.kane.hotel.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Integer> {

    @Query("SELECT b FROM Booking b WHERE b.booker = :booker")
    List<Booking> findByUser(@Param("booker") User booker);
}

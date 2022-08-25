package com.kane.hotel.dao;

import com.kane.hotel.model.Ad;
import org.springframework.data.repository.CrudRepository;

public interface AdRepository extends CrudRepository<Ad, Integer> {
    Ad findBySlug(String slug);
    Ad findByTitle(String title);

}

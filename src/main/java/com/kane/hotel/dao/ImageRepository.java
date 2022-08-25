package com.kane.hotel.dao;

import com.kane.hotel.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Integer> {
}

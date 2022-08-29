package com.kane.hotel.service;

import com.kane.hotel.model.Image;
import org.springframework.stereotype.Service;

public interface ImageService {

    Image ajouter(Image image);
    Image modifier(Image image, Integer id);
}

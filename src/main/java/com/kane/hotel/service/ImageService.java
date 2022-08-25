package com.kane.hotel.service;

import com.kane.hotel.model.Image;
import org.springframework.stereotype.Service;

@Service
public interface ImageService {

    public Image ajouter(Image image);
    public Image modifier(Image image, Integer id);
}

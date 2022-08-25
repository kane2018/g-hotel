package com.kane.hotel.service;

import com.kane.hotel.dao.ImageRepository;
import com.kane.hotel.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepository repository;

    @Override
    public Image ajouter(Image image) {
        return repository.save(image);
    }

    @Override
    public Image modifier(Image image, Integer id) {
        Image imageU = repository.findById(id).get();

        imageU.setUrl(image.getUrl());
        imageU.setCaption(image.getCaption());
        imageU.setAd(image.getAd());

        return repository.save(imageU);
    }
}

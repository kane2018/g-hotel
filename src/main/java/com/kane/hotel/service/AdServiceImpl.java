package com.kane.hotel.service;

import com.kane.hotel.dao.AdRepository;
import com.kane.hotel.dao.ImageRepository;
import com.kane.hotel.model.Ad;
import com.kane.hotel.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdServiceImpl implements AdService{

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Ad ajouter(Ad ad) {
        Ad a = adRepository.findByTitle(ad.getTitle());
        if(a == null)
        return adRepository.save(ad);
        else throw new ServiceException(
                "Ce titre existe déjà"
        );
    }

    @Override
    public List<Ad> getList() {
        return (List<Ad>) adRepository.findAll();
    }

    @Override
    public Ad getAd(String slug) {
        if(adRepository.findBySlug(slug) != null)
        return adRepository.findBySlug(slug);
        else throw new ServiceException("Annonce introuvable");
    }

    @Override
    public Ad modifier(Ad ad, Integer id) {
        Ad adU = adRepository.findById(id).get();
        adU.setTitle(ad.getTitle());
        adU.setIntroduction(ad.getIntroduction());
        adU.setContent(ad.getContent());
        adU.setCoverImage(ad.getCoverImage());
        adU.setRooms(ad.getRooms());
        adU.setPrice(ad.getPrice());
        System.out.println(ad.getImages().size());
        for (Image image: adU.getImages()
             ) {
            imageRepository.delete(image);
        }
        return adRepository.save(adU);
    }
}

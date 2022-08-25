package com.kane.hotel.service;

import com.kane.hotel.model.Ad;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdService {

    Ad ajouter(Ad ad);

    List<Ad> getList();

    Ad getAd(String slug);

    Ad modifier(Ad ad, Integer id);

}

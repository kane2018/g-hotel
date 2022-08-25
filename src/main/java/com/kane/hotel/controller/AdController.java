package com.kane.hotel.controller;

import com.kane.hotel.configuration.MyUserDetails;
import com.kane.hotel.model.Ad;
import com.kane.hotel.model.Image;
import com.kane.hotel.service.AdService;
import com.kane.hotel.service.ImageService;
import com.kane.hotel.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AdController {

    @Autowired
    private AdService adService;

    @Autowired
    ImageService imageService;


    @GetMapping(value = "/ads", name = "ads_index")
    public String index(Model model)
    {
        model.addAttribute("ads", adService.getList());
        return "ad/index";
    }

    @GetMapping(value = "/ads/{slug}", name = "ads_show")
    public String show(@PathVariable("slug") String slug, Model model) {
        try{
            Ad ad = adService.getAd(slug);
            model.addAttribute("ad", ad);
            return "ad/show";
        } catch (ServiceException e) {
            return "error/error-404";
        }

    }

    @GetMapping(value = "/ads/new", name = "ads_create_form")
    public String createForm(Model model) {
        Ad ad = new Ad();
        model.addAttribute("ad", ad);
        return "ad/create";
    }

    @PostMapping(value = "/ads/new", name = "ads_create_submit")
    public String createSubmit(@Valid Ad ad, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
        if(bindingResult.hasErrors()) {
            return "ad/create";
        }

        Object m = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUserDetails u = (MyUserDetails) m;

        ad.setAuthor(u.getUser());

        try {
            adService.ajouter(ad);
            for (Image image: ad.getImages()
            ) {
                image.setAd(ad);
                imageService.ajouter(image);
            }
        } catch (ServiceException exception) {
            model.addAttribute("warning", exception.getMessage());
            return "ad/create";
        }

        attributes.addFlashAttribute("success", "L'annonce "+ad.getTitle()+" a été bien ajoutée");

        return "redirect:/ads/"+ad.getSlug();
    }

    @GetMapping(value = "/ads/{slug}/edit", name = "ads_edit")
    public String edit(@PathVariable("slug") String slug, Model model) {

        try{Ad ad = adService.getAd(slug);
        model.addAttribute("ad", ad);
        return "ad/edit";
    } catch (ServiceException e) {
        return "error/404";
    }
    }

    @PostMapping(value = "/ads/edit/{id}", name = "ads_edit_submit")
    public String editSubmit(@PathVariable("id") Integer id, @Valid Ad ad, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "ad/edit";
        }

        Ad nAd = adService.modifier(ad, id);

        String slug = nAd.getSlug();

        for (Image image: ad.getImages()
        ) {
            image.setAd(ad);
            imageService.ajouter(image);
        }

        return "redirect:/ads/"+slug;
    }

}

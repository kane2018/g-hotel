package com.kane.hotel.controller;

import com.kane.hotel.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminAdController {

    @Autowired
    AdService adService;

    @GetMapping(value = "/admin/ads", name = "admin_ads_index")
    public String index(Model model) {
        model.addAttribute("ads", adService.getList());
        return "admin/ad/index";
    }
}

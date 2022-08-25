package com.kane.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/", name = "home_index")
    public String index() {
        return "home";
    }
}

package com.kane.hotel.controller;

import com.kane.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user/{id}", name = "user_show")
    public String index(@PathVariable("id") Integer id, Model model){
        model.addAttribute("user", userService.getUser(id));
        return "user/index";
    }
}

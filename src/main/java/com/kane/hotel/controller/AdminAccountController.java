package com.kane.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminAccountController {

    @GetMapping(value = "/admin/login", name = "admin_account_login")
    public String login(@RequestParam(value = "error", defaultValue = "false") boolean loginError, Model model) {
        if(loginError) {
            model.addAttribute("error", "Login et/ou mot de passe incorrects");
        }
        return "admin/account/login";
    }

    @GetMapping(value = "/admin/logout", name = "admin_account_logout")
    public String logout() {
        return "redirect:/admin/login";
    }

    @GetMapping(value = "/admin", name = "admin_account_index")
    public String index() {
        return "admin/index";
    }
}

package com.kane.hotel.controller;

import com.kane.hotel.configuration.MyUserDetails;
import com.kane.hotel.model.Role;
import com.kane.hotel.model.User;
import com.kane.hotel.service.RoleService;
import com.kane.hotel.service.ServiceException;
import com.kane.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/login", name = "account_login")
    public String login(@RequestParam(value = "error", defaultValue = "false") boolean loginError, Model model) {
        if(loginError) {
            model.addAttribute("error", "Login et/ou mot de passe incorrects");
        }
        return "account/login";
    }

    @GetMapping(value = "/register", name = "account_register_form")
    public String registerForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "account/register";
    }

    @PostMapping(value = "register", name = "account_register_submit")
    public String registerSubmit(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
        if(bindingResult.hasErrors()) {
            return "account/register";
        }

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash = encoder.encode(user.getHash());
            user.setHash(hash);
            user.passwordConfirm = hash;
            Role r = roleService.getRole("ROLE_USER");
            user.getRoles().add(r);
            userService.ajouter(user);
        } catch (ServiceException exception) {
            model.addAttribute("warning", exception.getMessage());
            return "account/register";
        }

        attributes.addFlashAttribute("success", "Votre compte a été bien crée ! Vous pouvez maintenant vous connecter !");
        return "redirect:/login";
    }

    @GetMapping(value = "/account/profile", name = "account_profile")
    public String profile(Model model) {

        Object m = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUserDetails u = (MyUserDetails) m;
        User user = u.getUser();
        model.addAttribute("user", user);
        return "account/profile";
    }

    @PostMapping(value = "/account/profile", name = "account_doProfile")
    public String doProfile(@Valid User user, Model model) {

        Object m = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUserDetails u = (MyUserDetails) m;
        User us = u.getUser();

        userService.modifier(user, us.getId());

        model.addAttribute("success", "Les données ont été bien modifiées");
        return "account/profile";
    }

    @GetMapping(value = "/account/password-update", name = "account_password")
    public String updatePassword(Model model) {
        model.addAttribute("user", new User());
        return "account/password";
    }

    @PostMapping(value = "/account/doPassword", name = "account_password_submit")
    public String doPassword(@Valid User user, BindingResult result, RedirectAttributes attributes) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Object m = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUserDetails u = (MyUserDetails) m;
        User us = u.getUser();

        boolean verify = encoder.matches(user.old, us.getHash());
        if(!verify) {
            result.rejectValue("old", null, "L'ancien mot de passe ne correspond pas");
            return "account/password";
        }

        String hash = encoder.encode(user.getHash());
        user.setHash(hash);

        userService.changePassword(user, us.getId());

        attributes.addFlashAttribute("success", "Votre mot de passe a été réinitialisé !");

        return "redirect:/";
    }
}

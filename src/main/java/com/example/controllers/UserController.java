package com.example.controllers;

import com.example.models.User;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/registration")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "logIn/registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "logIn/registration";
        } else {
            if (!userService.saveUser(user)) {
                model.addAttribute("messageExist", "user with this login is already registered");
                return "logIn/registration";
            }
        }
        return "redirect:/login";
    }
}

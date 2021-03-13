package com.example.controllers;

import com.example.models.User;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String addUser(User user, Model model) {

        if(!userService.saveUser(user)){
            model.addAttribute("message", "User exists");
            return "registration";
        }
        return "redirect:/login";
    }
}

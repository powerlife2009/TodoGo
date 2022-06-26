package com.example.todogo.controllers;

import com.example.todogo.models.User;
import com.example.todogo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.example.todogo.constants.TodoGoConstants.*;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/registration")
    public String registerForm(Model model) {
        model.addAttribute(USER, new User());

        return LOGIN_REGISTRATION;
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return LOGIN_REGISTRATION;
        } else {
            if (!userService.saveUser(user)) {
                model.addAttribute("messageExist", "user with this login is already registered");

                return LOGIN_REGISTRATION;
            }
        }

        return REDIRECT_TO_LOGIN;
    }
}

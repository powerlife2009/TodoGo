package com.main.todogo.controllers;

import com.main.todogo.constants.TodoGoConstants;
import com.main.todogo.models.User;
import com.main.todogo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/registration")
    public String registerForm(Model model) {
        model.addAttribute(TodoGoConstants.USER, new User());

        return TodoGoConstants.LOGIN_REGISTRATION;
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return TodoGoConstants.LOGIN_REGISTRATION;
        } else {
            if (!userService.saveUser(user)) {
                model.addAttribute("messageExist", "user with this login is already registered");

                return TodoGoConstants.LOGIN_REGISTRATION;
            }
        }

        return TodoGoConstants.REDIRECT_TO_LOGIN;
    }
}

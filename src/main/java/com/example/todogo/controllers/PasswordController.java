package com.example.todogo.controllers;

import com.example.todogo.constants.TodoGoConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class PasswordController {

    @GetMapping("/myPasswords")
    public String toPasswordsPage() {
        return TodoGoConstants.USER_PASSWORDS_PAGE;
    }
}

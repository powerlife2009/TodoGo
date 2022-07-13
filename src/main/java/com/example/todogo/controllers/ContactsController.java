package com.example.todogo.controllers;

import com.example.todogo.constants.TodoGoConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myContacts")
@AllArgsConstructor
public class ContactsController {

    @GetMapping
    public String toContactsPage() {
        return TodoGoConstants.USER_CONTACTS_PAGE;
    }
}

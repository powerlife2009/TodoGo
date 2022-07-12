package com.example.todogo.controllers;

import com.example.todogo.constants.TodoGoConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ContactsController {

    @GetMapping("/myContacts")
    public String toContactsPage() {
        return TodoGoConstants.USER_CONTACTS_PAGE;
    }
}

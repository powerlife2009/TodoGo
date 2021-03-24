package com.example.controllers;

import com.example.models.Groups;
import com.example.models.Task;
import com.example.models.User;
import com.example.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    private final ListUtils listUtils;

    @Autowired
    public MainController(ListUtils listUtils) {
        this.listUtils = listUtils;
    }


    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("newTask", new Task());
        model.addAttribute("groups", Groups.values());
        //model.addAttribute("tasks", listUtils.myTodoQueue(user));
        return "main";
    }
}

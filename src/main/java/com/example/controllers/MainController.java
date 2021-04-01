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

    @GetMapping("/")
    public String startPage(@AuthenticationPrincipal User user) {
        if (user == null) {
            return "guest_page";
        }
        return "home_page";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user, Model model) {
        System.out.println(user.getName());
        model.addAttribute("newTask", new Task());
        model.addAttribute("groups", Groups.values());
        model.addAttribute("nearest", listUtils.getTaskSorting().sortTodoByDateAndGetNearestEvents(user));
        model.addAttribute("defaultList", listUtils.getTaskSorting().sortTodoAsQueue(user));
        return "main";
    }
}
package com.example.todogo.controllers;

import com.example.todogo.models.Groups;
import com.example.todogo.models.Role;
import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class MainController {

    private final TaskService taskService;

    @Autowired
    public MainController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String startPage(@AuthenticationPrincipal User user) {
        if (user == null) {
            return "guest_page";
        }
        if (user.getRole() == Role.ROLE_ADMIN) {
            return "redirect:/admin";
        }
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("newTask", new Task());
        model.addAttribute("groups", Groups.values());
        model.addAttribute("nearest", taskService.getTasksSortedByDateAndNearestFive(user));
        model.addAttribute("defaultList", taskService.sortTasksAsQueue(user));
        return "user/main_page";
    }
}
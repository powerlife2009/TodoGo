package com.example.todogo.controllers;

import com.example.todogo.models.Groups;
import com.example.todogo.models.Role;
import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.todogo.constants.TodoGoConstants.*;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class MainController {

    private final TaskService taskService;

    @GetMapping
    public String startPage(@AuthenticationPrincipal User user) {
        if (user != null) {
            if (user.getRole().equals(Role.ROLE_ADMIN)) {
                return REDIRECT_TO_ADMIN;
            }
            return REDIRECT_TO_MAIN_PAGE;
        }

        return GUEST_PAGE;
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(NEW_TASK, new Task());
        model.addAttribute(GROUPS, Groups.values());
        model.addAttribute(NEAREST, taskService.getTasksSortedByDateAndNearestFive(user));
        model.addAttribute(DEFAULT_LIST, taskService.sortTasksAsQueue(user));
        return USER_MAIN_PAGE;
    }
}
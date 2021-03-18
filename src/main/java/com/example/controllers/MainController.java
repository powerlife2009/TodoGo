package com.example.controllers;

import com.example.models.Task;
import com.example.models.User;
import com.example.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDate;


@Controller
@RequestMapping("/main")
public class MainController {

    private final TaskService taskService;

    @Autowired
    public MainController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    public String main(@AuthenticationPrincipal User user, Model model) {
        Iterable<Task> taskList = taskService.findAllByUser(user);
        model.addAttribute("tasks", taskList);
        return "main";
    }

    @PostMapping
    public String createTodo(@AuthenticationPrincipal User user,
                             @RequestParam String text, String date, String type, Integer priority, Model model) {

        Task task = new Task(text, user,LocalDate.parse(date), type, priority);
        taskService.saveTask(task);
        Iterable<Task> taskList = taskService.findAllByUser(user);
        model.addAttribute("tasks", taskList);
        return "main";
    }
}

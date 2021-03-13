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
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

    private final TaskService taskService;

    @Autowired
    public MainController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user, Model model) {
        Iterable<Task> taskList = taskService.findAllByUser(user);
        model.addAttribute("tasks", taskList);
        return "main";
    }

    @PostMapping("/main")
    public String createTodo(@AuthenticationPrincipal User user,
                             @RequestParam String text, Model model) {
        Task task = new Task(text, user);
        taskService.saveTask(task);
        Iterable<Task> taskList = taskService.findAllByUser(user);
        model.addAttribute("tasks", taskList);
        return "main";
    }
}

package com.example.controllers;

import com.example.models.Task;
import com.example.models.User;
import com.example.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TaskListController {

    private final TaskService taskService;

    @Autowired
    public TaskListController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/byDate")
    public String sortByDate(@AuthenticationPrincipal User user, Model model) {
        List<Task> tasks = taskService.findAllByUser(user);
        tasks = tasks.stream()
                .sorted(Comparator.comparing(Task::getDate))
                .collect(Collectors.toList());
        model.addAttribute("tasks", tasks);
        return "main";
    }

    @PostMapping("/byPriority")
    public String sortByPriority(@AuthenticationPrincipal User user, Model model) {
        List<Task> tasks = taskService.findAllByUser(user);
        tasks = tasks.stream()
                .sorted(Comparator.comparing(Task::getPriority).reversed())
                .collect(Collectors.toList());
        model.addAttribute("tasks", tasks);
        return "main";
    }

    @PostMapping("/byType")
    public String sortByType(@AuthenticationPrincipal User user, Model model) {
        List<Task> tasks = taskService.findAllByUser(user);
        tasks = tasks.stream()
                .sorted(Comparator.comparing(Task::getType))
                .collect(Collectors.toList());
        model.addAttribute("tasks", tasks);
        return "main";
    }
}

package com.example.controllers;

import com.example.models.Task;
import com.example.models.User;
import com.example.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;


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
        model.addAttribute("newTask", new Task());
        model.addAttribute("tasks", myTodoList(user));
        return "main";
    }

    @PostMapping("/save")
    public String saveTodo(@AuthenticationPrincipal User user,
                           @Valid @ModelAttribute Task newTask,
                           BindingResult errors,
                           Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("message", "has errors");
        } else {
            newTask.setUser(user);
            taskService.saveTask(newTask);
            model.addAttribute("message", "successfully");
        }
        model.addAttribute("newTask", new Task());
        model.addAttribute("tasks", myTodoList(user));
        return "main";
    }


    @PostMapping("/delete")
    public String deleteTodo(@AuthenticationPrincipal User user,
                             @RequestParam Long id,
                             Model model) {
        taskService.deleteTask(id);
        model.addAttribute("message", "successfully");
        model.addAttribute("newTask", new Task());
        model.addAttribute("tasks", myTodoList(user));
        return "main";
    }

    public List<Task> myTodoList(User user) {
        List<Task> taskList = taskService.findAllByUser(user);
        Collections.reverse(taskList);
        return taskList;
    }
}

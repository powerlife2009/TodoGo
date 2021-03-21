package com.example.controllers;

import com.example.models.Task;
import com.example.models.User;
import com.example.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.text.spi.DateFormatProvider;
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
        listTodo(user, model);
        return "main";
    }

    @PostMapping("/create")
    public String createTodo(@AuthenticationPrincipal User user,
                             @RequestParam String text,
                             @RequestParam String date,
                             @RequestParam String type,
                             @RequestParam Integer priority) {

        Task task = new Task(text, user, Date.valueOf(date), type, priority);
        taskService.saveTask(task);
        return "redirect:/main";
    }

    @PostMapping("/delete")
    public String deleteTodo(@RequestParam String id) {
        Long todoId = Long.parseLong(id);
        taskService.deleteTodo(todoId);
        return "redirect:/main";
    }

    @PostMapping("/edit")
    public String editTodo(@RequestParam String id,
                           @RequestParam String text,
                           @RequestParam String date,
                           @RequestParam String type,
                           @RequestParam Integer priority) {
        Long todoId = Long.parseLong(id);

        Task task = taskService.getTask(todoId);

        task.setText(text);
        task.setDate(Date.valueOf(date));
        task.setPriority(priority);
        task.setType(type);

        taskService.saveTask(task);

        return "redirect:/main";
    }


    public void listTodo(User user, Model model) {
        Iterable<Task> taskList = taskService.findAllByUser(user);
        model.addAttribute("tasks", taskList);
    }

}

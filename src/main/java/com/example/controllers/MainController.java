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
    public String deleteTodo(@RequestParam Long id) {
        taskService.deleteTodo(id);
        return "redirect:/main";
    }

    @PostMapping("/edit")
    public String editTodo(@RequestParam Long id,
                           @RequestParam String text,
                           @RequestParam String date,
                           @RequestParam String type,
                           @RequestParam Integer priority) {

        Task task = taskService.getTask(id);

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

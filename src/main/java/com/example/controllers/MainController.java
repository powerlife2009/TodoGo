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

    @PostMapping("/create")
    public String createTodo(@AuthenticationPrincipal User user,
                             @ModelAttribute("newTask") Task newTask) {
        newTask.setUser(user);
        taskService.saveTask(newTask);
        return "redirect:/main";
    }

    @PostMapping("/delete")
    public String deleteTodo(@RequestParam Long id) {
        taskService.deleteTask(id);
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

    public List<Task> myTodoList(User user) {
        List<Task>taskList =taskService.findAllByUser(user);
        Collections.reverse(taskList);
        return taskList;
    }
}

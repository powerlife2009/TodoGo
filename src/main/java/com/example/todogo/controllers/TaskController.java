package com.example.todogo.controllers;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PostMapping("/save")
    public String saveTodo(@AuthenticationPrincipal User user,
                           @Valid @ModelAttribute Task newTask,
                           BindingResult errors,
                           RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "has errors");
        } else {
            newTask.setUser(user);
            taskService.saveTask(newTask);
            redirectAttributes.addFlashAttribute("message", "successfully");
        }
        redirectAttributes.addFlashAttribute("tasks", taskService.sortTasksAsQueue(user));
        return "redirect:/main";
    }

    @PostMapping("/delete")
    public String deleteTodo(@AuthenticationPrincipal User user,
                             @RequestParam Long id,
                             RedirectAttributes redirectAttributes) {
        taskService.deleteTask(id);
        redirectAttributes.addFlashAttribute("message", "successfully");
        redirectAttributes.addFlashAttribute("tasks", taskService.sortTasksAsQueue(user));
        return "redirect:/main";
    }
}

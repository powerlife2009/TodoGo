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

import static com.example.todogo.constants.TodoGoConstants.*;

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
            redirectAttributes.addFlashAttribute(MESSAGE, HAS_ERRORS);
        } else {
            taskService.saveTask(newTask, user);
            redirectAttributes.addFlashAttribute(MESSAGE, SUCCESSFULLY);
        }

        redirectAttributes.addFlashAttribute(TODO_LIST, taskService.sortTasksAsQueue(user));

        return REDIRECT_TO_MAIN_PAGE;
    }

    @PostMapping("/delete")
    public String deleteTodo(@AuthenticationPrincipal User user,
                             @RequestParam Long id,
                             RedirectAttributes redirectAttributes) {
        taskService.deleteTaskById(id);
        redirectAttributes.addFlashAttribute(MESSAGE, SUCCESSFULLY);
        redirectAttributes.addFlashAttribute(TODO_LIST, taskService.sortTasksAsQueue(user));

        return REDIRECT_TO_MAIN_PAGE;
    }
}

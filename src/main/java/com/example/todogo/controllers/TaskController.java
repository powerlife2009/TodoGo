package com.example.todogo.controllers;

import com.example.todogo.constants.TodoGoConstants;
import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/myTasks/save")
    public String saveTodo(@Valid @ModelAttribute Task newTask,
                           BindingResult errors,
                           @RequestParam Optional<String> taskId,
                           @AuthenticationPrincipal User user,
                           RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.HAS_ERRORS);
        } else {
            taskId.ifPresent(id -> newTask.setTaskId(Long.parseLong(id)));

            taskService.saveTask(newTask, user);
            redirectAttributes.addFlashAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.SUCCESSFULLY);
        }

        return TodoGoConstants.REDIRECT_TO_TASKS_PAGE;
    }

    @PostMapping("/myTasks/{taskId}/delete")
    public String deleteTask(@PathVariable("taskId") Long taskId,
                             RedirectAttributes redirectAttributes) {
        taskService.deleteTaskById(taskId);
        redirectAttributes.addFlashAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.SUCCESSFULLY);

        return TodoGoConstants.REDIRECT_TO_TASKS_PAGE;
    }
}

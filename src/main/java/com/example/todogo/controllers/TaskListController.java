package com.example.todogo.controllers;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.example.todogo.constants.TodoGoConstants.*;

@Controller
@AllArgsConstructor
public class TaskListController {

    private final TaskService taskService;


    @PostMapping("/sort")
    public String sortBy(@AuthenticationPrincipal User user,
                         @RequestParam String sortBy,
                         RedirectAttributes redirectAttributes) {
        List<Task> sortedTaskList = taskService.getAllTaskSortedBy(user, sortBy);
        redirectAttributes.addFlashAttribute(TASKS, sortedTaskList);
        return REDIRECT_TO_MAIN_PAGE;
    }


    @PostMapping("/search")
    public String search(@AuthenticationPrincipal User user,
                         @RequestParam String searchText,
                         RedirectAttributes redirectAttributes) {
        List<Task> findTaskList = taskService.searchTaskByText(user, searchText);
        if (findTaskList.isEmpty()) {
            redirectAttributes.addFlashAttribute(MESSAGE, NOT_FOUND);
        }
        redirectAttributes.addFlashAttribute(TASKS, findTaskList);
        return REDIRECT_TO_MAIN_PAGE;
    }


    @PostMapping("/filterByType")
    public String filterByType(@AuthenticationPrincipal User user,
                               @RequestParam String type,
                               RedirectAttributes redirectAttributes) {
        List<Task> filterTaskList = taskService.filterAllTasksByType(user, type);
        if (filterTaskList.isEmpty()) {
            redirectAttributes.addFlashAttribute(MESSAGE, NOT_FOUND);
        }
        redirectAttributes.addFlashAttribute(TASKS, filterTaskList);
        return REDIRECT_TO_MAIN_PAGE;
    }

    @PostMapping("/filterByPriority")
    public String filterByPriority(@AuthenticationPrincipal User user,
                                   @RequestParam Integer priority,
                                   RedirectAttributes redirectAttributes) {
        List<Task> filterTaskList = taskService.filterAllTasksByPriority(user, priority);
        if (filterTaskList.isEmpty()) {
            redirectAttributes.addFlashAttribute(MESSAGE, NOT_FOUND);
        }
        redirectAttributes.addFlashAttribute(TASKS, filterTaskList);
        return REDIRECT_TO_MAIN_PAGE;
    }
}

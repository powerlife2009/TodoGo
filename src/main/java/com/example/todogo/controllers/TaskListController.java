package com.example.todogo.controllers;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TaskListController {


    private final TaskService taskService;

    @Autowired
    public TaskListController(TaskService taskService) {
        this.taskService = taskService;
    }


    @PostMapping("/sort")
    public String sortBy(@AuthenticationPrincipal User user,
                         @RequestParam String sortBy,
                         RedirectAttributes redirectAttributes) {
        List<Task> sortedTaskList = taskService.getAllTaskSortedBy(user, sortBy);
        redirectAttributes.addFlashAttribute("tasks", sortedTaskList);
        return "redirect:/main";
    }


    @PostMapping("/search")
    public String search(@AuthenticationPrincipal User user,
                         @RequestParam String searchText,
                         RedirectAttributes redirectAttributes) {
        List<Task> findTaskList = taskService.searchTaskByText(user, searchText);
        if (findTaskList.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "not found");
        }
        redirectAttributes.addFlashAttribute("tasks", findTaskList);
        return "redirect:/main";
    }


    @PostMapping("/filterByType")
    public String filterByType(@AuthenticationPrincipal User user,
                               @RequestParam String type,
                               RedirectAttributes redirectAttributes) {
        List<Task> filterTaskList = taskService.filterAllTasksByType(user, type);
        if (filterTaskList.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "not found");
        }
        redirectAttributes.addFlashAttribute("tasks", filterTaskList);
        return "redirect:/main";
    }

    @PostMapping("/filterByPriority")
    public String filterByPriority(@AuthenticationPrincipal User user,
                                   @RequestParam Integer priority,
                                   RedirectAttributes redirectAttributes) {
        List<Task> filterTaskList = taskService.filterAllTasksByPriority(user, priority);
        if (filterTaskList.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "not found");
        }
        redirectAttributes.addFlashAttribute("tasks", filterTaskList);
        return "redirect:/main";
    }
}

package com.example.controllers;

import com.example.models.Task;
import com.example.models.User;
import com.example.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TaskListController {

    private final ListUtils listUtils;

    @Autowired
    public TaskListController(ListUtils listUtils) {
        this.listUtils = listUtils;
    }


    @PostMapping("/sort")
    public String sortBy(@AuthenticationPrincipal User user,
                         @RequestParam String sortBy,
                         RedirectAttributes redirectAttributes) {
        List<Task> sortedTaskList = listUtils.getTaskSorting().sortTodoBy(sortBy, user);
        redirectAttributes.addFlashAttribute("tasks", sortedTaskList);
        return "redirect:/main";
    }


    @PostMapping("/search")
    public String search(@AuthenticationPrincipal User user,
                         @RequestParam String searchText,
                         RedirectAttributes redirectAttributes) {
        List<Task> findTaskList = listUtils.getTaskSearch().searchTodoByText(searchText, user);
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
        List<Task> filterTaskList = listUtils.getTaskFilter().filterTodoByType(type, user);
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
        List<Task> filterTaskList = listUtils.getTaskFilter().filterTodoByPriority(priority, user);
        if(filterTaskList.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "not found");
        }
        redirectAttributes.addFlashAttribute("tasks", filterTaskList);
        return "redirect:/main";
    }
}

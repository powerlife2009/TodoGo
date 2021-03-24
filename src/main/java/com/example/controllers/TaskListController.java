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
        List<Task> sortedTaskList = listUtils.sortTodoBy(sortBy, user);
        redirectAttributes.addFlashAttribute("tasks", sortedTaskList);
        return "redirect:/main";
    }


    @PostMapping("/search")
    public String search(@AuthenticationPrincipal User user,
                         @RequestParam String searchText,
                         RedirectAttributes redirectAttributes) {
        List<Task> findTaskList = listUtils.searchTodoByText(searchText, user);

        if (findTaskList.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "not found");
        }
        redirectAttributes.addFlashAttribute("tasks", findTaskList);
        return "redirect:/main";
    }


    @PostMapping("/filter")
    public String filterBy(@AuthenticationPrincipal User user,
                           @RequestParam String filterBy,
                           RedirectAttributes redirectAttributes) {
        List<Task> filterTaskList = listUtils.filterTodoBy(filterBy, user);
        if (filterTaskList.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "not found");
        }
        redirectAttributes.addFlashAttribute("tasks", filterTaskList);
        return "redirect:/main";
    }
}

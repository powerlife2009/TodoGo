package com.example.controllers;

import com.example.models.Task;
import com.example.models.User;
import com.example.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UtilsController {

    private final ListUtils listUtils;

    @Autowired
    public UtilsController(ListUtils listUtils) {
        this.listUtils = listUtils;
    }


    @PostMapping("/sortMyTasks")
    public String sortByPriority(@AuthenticationPrincipal User user,
                                 @RequestParam String sortBy,
                                 Model model) {
        List<Task> sortedTaskList = listUtils.sortTaskBy(sortBy, user);
        model.addAttribute("newTask", new Task());
        model.addAttribute("tasks", sortedTaskList);
        return "main";
    }


    @PostMapping("/search")
    public String searchTodo(@AuthenticationPrincipal User user,
                             @RequestParam String searchText,
                             Model model) {
        List<Task> findTaskList = listUtils.searchTask(searchText, user);

        if (findTaskList.isEmpty()) {
            model.addAttribute("message", "not found");
            model.addAttribute("newTask", new Task());
            model.addAttribute("tasks", findTaskList);
            return "main";
        } else {
            model.addAttribute("newTask", new Task());
            model.addAttribute("tasks", findTaskList);
        }
        return "main";
    }
}

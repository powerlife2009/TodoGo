package com.example.todogo.controllers;

import com.example.todogo.models.Groups;
import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.example.todogo.constants.TodoGoConstants.*;

@Controller
@AllArgsConstructor
public class TaskListController {

    private final TaskService taskService;

    @GetMapping("/myTasks/sort")
    public String sortBy(@AuthenticationPrincipal User user, @RequestParam String sortBy, Model model) {
        List<Task> sortedTaskList = taskService.getAllTaskSortedBy(user, sortBy);

        model.addAttribute(NEW_TASK, new Task());
        model.addAttribute(GROUPS, Groups.values());
        model.addAttribute(NEAREST, taskService.getTasksSortedByDateAndNearestFive(user));
        model.addAttribute(TODO_LIST, sortedTaskList);

        return USER_TASKS_PAGE;
    }

    @PostMapping("/myTasks/search")
    public String search(@AuthenticationPrincipal User user, @RequestParam String searchText, Model model) {
        List<Task> taskList = taskService.searchTaskByText(user, searchText);

        if (taskList.isEmpty()) {
            model.addAttribute(ACTION_RESULT, NOT_FOUND);
        }

        model.addAttribute(NEW_TASK, new Task());
        model.addAttribute(GROUPS, Groups.values());
        model.addAttribute(NEAREST, taskService.getTasksSortedByDateAndNearestFive(user));
        model.addAttribute(TODO_LIST, taskList);

        return USER_TASKS_PAGE;
    }

    @GetMapping("/myTasks/filterByType")
    public String filterByType(@AuthenticationPrincipal User user, @RequestParam String type, Model model) {
        List<Task> filteredTaskList = taskService.filterAllTasksByType(user, type);

        if (filteredTaskList.isEmpty()) {
            model.addAttribute(ACTION_RESULT, NOT_FOUND);
        }

        model.addAttribute(NEW_TASK, new Task());
        model.addAttribute(GROUPS, Groups.values());
        model.addAttribute(NEAREST, taskService.getTasksSortedByDateAndNearestFive(user));
        model.addAttribute(TODO_LIST, filteredTaskList);

        return USER_TASKS_PAGE;
    }

    @GetMapping("/myTasks/filterByPriority")
    public String filterByPriority(@AuthenticationPrincipal User user, @RequestParam Integer priority,
            Model model) {
        List<Task> filteredTaskList = taskService.filterAllTasksByPriority(user, priority);

        if (filteredTaskList.isEmpty()) {
            model.addAttribute(ACTION_RESULT, NOT_FOUND);
        }

        model.addAttribute(NEW_TASK, new Task());
        model.addAttribute(GROUPS, Groups.values());
        model.addAttribute(NEAREST, taskService.getTasksSortedByDateAndNearestFive(user));
        model.addAttribute(TODO_LIST, filteredTaskList);

        return USER_TASKS_PAGE;
    }
}

package com.example.todogo.controllers;

import com.example.todogo.constants.TodoGoConstants;
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

@Controller
@AllArgsConstructor
public class TaskListController {

    private final TaskService taskService;

    @GetMapping("/myTasks")
    public String toMyTasksPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(TodoGoConstants.NEW_TASK, new Task());
        model.addAttribute(TodoGoConstants.GROUPS, Groups.values());
        model.addAttribute(TodoGoConstants.NEAREST, taskService.getTasksSortedByDateAndNearestFive(user));
        model.addAttribute(TodoGoConstants.TODO_LIST, taskService.sortTasksAsQueue(user));

        return TodoGoConstants.USER_TASKS_PAGE;
    }

    @PostMapping("/myTasks/sort")
    public String sortBy(@AuthenticationPrincipal User user, @RequestParam String sortBy, Model model) {
        List<Task> sortedTaskList = taskService.getAllTaskSortedBy(user, sortBy);

        addAttributes(sortedTaskList, model, user);

        return TodoGoConstants.USER_TASKS_PAGE;
    }

    @PostMapping("/myTasks/search")
    public String search(@AuthenticationPrincipal User user, @RequestParam String searchText, Model model) {
        List<Task> taskList = taskService.searchTaskByText(user, searchText);

        addAttributes(taskList, model, user);

        return TodoGoConstants.USER_TASKS_PAGE;
    }

    @PostMapping("/myTasks/filterByType")
    public String filterByType(@AuthenticationPrincipal User user, @RequestParam String type, Model model) {
        List<Task> filteredTaskList = taskService.filterAllTasksByType(user, type);

        addAttributes(filteredTaskList, model, user);

        return TodoGoConstants.USER_TASKS_PAGE;
    }

    @PostMapping("/myTasks/filterByPriority")
    public String filterByPriority(@AuthenticationPrincipal User user, @RequestParam Integer priority,
            Model model) {
        List<Task> filteredTaskList = taskService.filterAllTasksByPriority(user, priority);

        addAttributes(filteredTaskList, model, user);

        return TodoGoConstants.USER_TASKS_PAGE;
    }

    private void addAttributes(List<Task> tasks, Model model, User user) {
        if (tasks.isEmpty()) {
            model.addAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.NOT_FOUND);
        }

        model.addAttribute(TodoGoConstants.NEW_TASK, new Task());
        model.addAttribute(TodoGoConstants.GROUPS, Groups.values());
        model.addAttribute(TodoGoConstants.NEAREST, taskService.getTasksSortedByDateAndNearestFive(user));
        model.addAttribute(TodoGoConstants.TODO_LIST, tasks);
    }
}

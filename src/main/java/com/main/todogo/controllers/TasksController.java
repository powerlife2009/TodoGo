package com.main.todogo.controllers;

import com.main.todogo.constants.TodoGoConstants;
import com.main.todogo.models.Groups;
import com.main.todogo.models.Task;
import com.main.todogo.models.User;
import com.main.todogo.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/myTasks")
@AllArgsConstructor
public class TasksController {

    private final TaskService taskService;

    @GetMapping
    public String toMyTasksPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(TodoGoConstants.NEW_TASK, new Task());
        model.addAttribute(TodoGoConstants.GROUPS, Groups.values());
        model.addAttribute(TodoGoConstants.NEAREST,
                taskService.getTasksSortedByDateAndNearestFive(user.getUserId()));
        model.addAttribute(TodoGoConstants.TODO_LIST, taskService.sortTasksAsQueue(user.getUserId()));

        return TodoGoConstants.USER_TASKS_PAGE;
    }

    @PostMapping("/sort")
    public String sortBy(@AuthenticationPrincipal User user, @RequestParam String sortBy, Model model) {
        List<Task> sortedTaskList = taskService.getAllTaskSortedBy(user.getUserId(), sortBy);

        addAttributes(sortedTaskList, model, user);

        return TodoGoConstants.USER_TASKS_PAGE;
    }

    @PostMapping("/search")
    public String searchTasks(@AuthenticationPrincipal User user, @RequestParam String searchText, Model model) {
        List<Task> taskList =
                taskService.searchTaskByText(user.getUserId(), searchText);

        addAttributes(taskList, model, user);

        return TodoGoConstants.USER_TASKS_PAGE;
    }

    @PostMapping("/filterByType")
    public String filterByType(@AuthenticationPrincipal User user, @RequestParam String type, Model model) {
        List<Task> filteredTaskList = taskService.filterAllTasksByType(user.getUserId(), type);

        addAttributes(filteredTaskList, model, user);

        return TodoGoConstants.USER_TASKS_PAGE;
    }

    @PostMapping("/filterByPriority")
    public String filterByPriority(@AuthenticationPrincipal User user, @RequestParam Integer priority,
            Model model) {
        List<Task> filteredTaskList = taskService.filterAllTasksByPriority(user.getUserId(), priority);

        addAttributes(filteredTaskList, model, user);

        return TodoGoConstants.USER_TASKS_PAGE;
    }

    @PostMapping("/save")
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

    @PostMapping("/{taskId}/delete")
    public String deleteTask(@PathVariable("taskId") Long taskId,
            RedirectAttributes redirectAttributes) {
        taskService.deleteTaskById(taskId);
        redirectAttributes.addFlashAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.SUCCESSFULLY);

        return TodoGoConstants.REDIRECT_TO_TASKS_PAGE;
    }

    private void addAttributes(List<Task> tasks, Model model, User user) {
        if (tasks.isEmpty()) {
            model.addAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.NOT_FOUND);
        }

        model.addAttribute(TodoGoConstants.NEW_TASK, new Task());
        model.addAttribute(TodoGoConstants.GROUPS, Groups.values());
        model.addAttribute(TodoGoConstants.NEAREST,
                taskService.getTasksSortedByDateAndNearestFive(user.getUserId()));
        model.addAttribute(TodoGoConstants.TODO_LIST, tasks);
    }
}

package com.example.todogo.utils.forTodoList;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskSorting implements Sorting {

    private final TaskService taskService;

    @Autowired
    public TaskSorting(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<Task> sortTodoBy(String sortBy, User user) {
        List<Task> tasks = taskService.findAllByUser(user);
        switch (sortBy) {
            case "date":
                tasks = tasks.stream()
                        .sorted(Comparator.comparing(Task::getDate))
                        .collect(Collectors.toList());
                break;

            case "priority":
                tasks = tasks.stream()
                        .sorted(Comparator.comparing(Task::getPriority).reversed())
                        .collect(Collectors.toList());
                break;

            case "type":
                tasks = tasks.stream()
                        .sorted(Comparator.comparing(Task::getType))
                        .collect(Collectors.toList());
                break;
        }
        return tasks;
    }

    @Override
    public List<Task> sortTodoAsQueue(User user) {
        List<Task> taskList = taskService.findAllByUser(user);
        Collections.reverse(taskList);
        return taskList;
    }

    @Override
    public List<Task> sortTodoByDateAndGetNearestEvents(User user) {
        List<Task> tasks = taskService.findAllByUser(user);
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getDate))
                .limit(5)
                .collect(Collectors.toList());
    }
}

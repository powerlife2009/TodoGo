package com.example.utils;

import com.example.models.Task;
import com.example.models.User;
import com.example.services.TaskService;
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
            case "date" -> tasks = tasks.stream()
                    .sorted(Comparator.comparing(Task::getDate))
                    .collect(Collectors.toList());

            case "priority" -> tasks = tasks.stream()
                    .sorted(Comparator.comparing(Task::getPriority).reversed())
                    .collect(Collectors.toList());

            case "type" -> tasks = tasks.stream()
                    .sorted(Comparator.comparing(Task::getType))
                    .collect(Collectors.toList());
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
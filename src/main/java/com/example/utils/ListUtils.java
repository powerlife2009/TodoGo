package com.example.utils;

import com.example.models.Task;
import com.example.models.User;
import com.example.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ListUtils implements Search, Filter, Sorting {

    private final TaskService taskService;

    @Autowired
    public ListUtils(TaskService taskService) {
        this.taskService = taskService;
    }


    public List<Task> myTodoQueue(User user) {
        List<Task> taskList = taskService.findAllByUser(user);
        Collections.reverse(taskList);
        return taskList;
    }

    @Override
    public List<Task> filterTodoBy(String filterBy, User user) {
        List<Task> tasks = taskService.findAllByUser(user);
        return tasks.stream()
                .filter(task -> task.getType().equals(filterBy))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> searchTodoByText(String searchText, User user) {
        List<Task> tasks = taskService.findAllByUser(user);
        return tasks.stream()
                .filter(task -> task.getText().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
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

    public List<Task> getNearestEvents(User user) {
        List<Task> tasks = taskService.findAllByUser(user);

        return tasks.stream()
                .sorted(Comparator.comparing(Task::getDate))
                .limit(5)
                .collect(Collectors.toList());
    }
}

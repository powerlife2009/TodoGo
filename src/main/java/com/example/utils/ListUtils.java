package com.example.utils;

import com.example.models.Task;
import com.example.models.User;
import com.example.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListUtils {
    private final TaskService taskService;

    @Autowired
    public ListUtils(TaskService taskService) {
        this.taskService = taskService;
    }

    public List<Task> sortTaskBy(String sortBy, User user) {
        List<Task> sortTaskList = taskService.findAllByUser(user);
        switch (sortBy) {
            case "date" -> sortTaskList = sortTaskList.stream()
                    .sorted(Comparator.comparing(Task::getDate))
                    .collect(Collectors.toList());

            case "priority" -> sortTaskList = sortTaskList.stream()
                    .sorted(Comparator.comparing(Task::getPriority).reversed())
                    .collect(Collectors.toList());

            case "type" -> sortTaskList = sortTaskList.stream()
                    .sorted(Comparator.comparing(Task::getType))
                    .collect(Collectors.toList());
        }
        return sortTaskList;
    }

    public List<Task> searchTask(String searchText, User user) {
        List<Task> tasks = taskService.findAllByUser(user);
        return tasks.stream()
                .filter(task -> task.getText().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }
}

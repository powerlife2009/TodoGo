package com.example.todogo.utils.forTodoList;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskSearch implements Search {

    private final TaskService taskService;

    @Autowired
    public TaskSearch(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<Task> searchTodoByText(String searchText, User user) {
        List<Task> tasks = taskService.findAllByUser(user);
        return tasks.stream()
                .filter(task -> task.getText().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }
}

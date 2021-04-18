package com.example.todogo.utils.forTodoList;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskFilter implements Filter {

    private final TaskService taskService;

    @Autowired
    public TaskFilter(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<Task> filterTodoByType(String filterBy, User user) {
        List<Task> tasks = taskService.findAllByUser(user);
        return tasks.stream()
                .filter(task -> task.getType().equals(filterBy))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> filterTodoByPriority(Integer priority, User user) {
        List<Task> tasks = taskService.findAllByUser(user);
        return tasks.stream()
                .filter(task -> task.getPriority().equals(priority))
                .collect(Collectors.toList());
    }
}

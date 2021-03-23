package com.example.services;

import com.example.models.Task;
import com.example.models.User;
import com.example.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

private final TaskRepository taskRepository;

@Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllByUser(User user) {
    return taskRepository.findAllByUser(user);
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
    taskRepository.deleteById(id);
    }

    public Task getTask(Long id) {
    return taskRepository.getOne(id);
    }
}

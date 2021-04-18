package com.example.todogo.services;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Task getTaskById(Long id) {
        return taskRepository.getOne(id);
    }

}

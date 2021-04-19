package com.example.todogo.services;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTaskSortedBy(User user, String sortBy) {
        return taskRepository.findAllByUser(user, Sort.by(sortBy));
    }

    public List<Task> sortTasksAsQueue(User user) {
        return taskRepository.findAllByUser(user, Sort.by(Sort.Direction.DESC, ("id")));
    }

    public List<Task> filterAllTasksByType(User user, String type) {
        return taskRepository.findAllByUserAndType(user, type);
    }

    public List<Task> filterAllTasksByPriority(User user, Integer priority) {
        return taskRepository.findAllByUserAndPriority(user, priority);
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }





    public List<Task> getTasksSortedByDateAndNearestFive(User user) {
        return taskRepository.findAllByUser(user).stream()
                .sorted(Comparator.comparing(Task::getDate))
                .limit(5)
                .collect(Collectors.toList());
    }


    public List<Task> searchTaskByText(User user, String searchText) {
        List<Task> tasks = taskRepository.findAllByUser(user);
        return tasks.stream()
                .filter(task -> task.getText().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }




}

package com.example.todogo.services;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<Task> getAllTaskSortedBy(User user, String sortBy) {
        if(sortBy.equals("priority")) {
            return taskRepository.findAllByUser(user, Sort.by(sortBy).descending());
        }
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

    public List<Task> getTasksSortedByDateAndNearestFive(User user) {
        return taskRepository.findFirst5ByUserOrderByDateAsc(user);
    }

    public List<Task> searchTaskByText(User user, String searchText) {
        return taskRepository.findAllByUserAndTextContainingIgnoreCase(user, searchText);
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


}

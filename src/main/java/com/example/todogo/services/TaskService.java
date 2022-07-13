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

    public List<Task> getAllTaskSortedBy(long userId, String sortBy) {
        Sort sort = Sort.by(sortBy);

        if(sortBy.equals("priority")) {
            sort = sort.descending();
        }
        return taskRepository.findAllByUserUserId(userId, sort);
    }

    public List<Task> sortTasksAsQueue(long userId) {
        return taskRepository.findAllByUserUserId(userId, Sort.by(Sort.Direction.DESC, ("taskId")));
    }

    public List<Task> filterAllTasksByType(long userId, String type) {
        return taskRepository.findAllByUserUserIdAndType(userId, type);
    }

    public List<Task> filterAllTasksByPriority(long userId, Integer priority) {
        return taskRepository.findAllByUserUserIdAndPriority(userId, priority);
    }

    public List<Task> getTasksSortedByDateAndNearestFive(long userId) {
        return taskRepository.findFirst5ByUserUserIdOrderByDateAsc(userId);
    }

    public List<Task> searchTaskByText(long userId, String searchText) {
        return taskRepository.findAllByUserUserIdAndTextContainingIgnoreCase(userId, searchText);
    }

    public void saveTask(Task task, User user) {
        task.setUser(user);
        taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}

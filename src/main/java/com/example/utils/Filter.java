package com.example.utils;

import com.example.models.Task;
import com.example.models.User;

import java.util.List;

public interface Filter {

    List<Task> filterTodoByType(String filterBy, User user);

    List<Task> filterTodoByPriority(Integer priority, User user);
}

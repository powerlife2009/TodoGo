package com.example.todogo.utils.forTodoList;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;

import java.util.List;

public interface Filter {

    List<Task> filterTodoByType(String filterBy, User user);

    List<Task> filterTodoByPriority(Integer priority, User user);
}

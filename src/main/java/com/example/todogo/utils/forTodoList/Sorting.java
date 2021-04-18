package com.example.todogo.utils.forTodoList;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;

import java.util.List;

public interface Sorting {

    List<Task> sortTodoBy(String sortBy, User user);

    List<Task> sortTodoAsQueue(User user);

    List<Task> sortTodoByDateAndGetNearestEvents(User user);
}

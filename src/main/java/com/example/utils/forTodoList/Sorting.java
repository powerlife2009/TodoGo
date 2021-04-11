package com.example.utils.forTodoList;

import com.example.models.Task;
import com.example.models.User;

import java.util.List;

public interface Sorting {

    List<Task> sortTodoBy(String sortBy, User user);

    List<Task> sortTodoAsQueue(User user);

    List<Task> sortTodoByDateAndGetNearestEvents(User user);
}

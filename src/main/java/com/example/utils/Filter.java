package com.example.utils;

import com.example.models.Task;
import com.example.models.User;

import java.util.List;

public interface Filter {

    List<Task> filterTodoBy(String filterBy, User user);
}

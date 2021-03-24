package com.example.utils;

import com.example.models.Task;
import com.example.models.User;

import java.util.List;

public interface Sorting {

    List<Task> sortTodoBy(String sortBy, User user);
}

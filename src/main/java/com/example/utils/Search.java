package com.example.utils;

import com.example.models.Task;
import com.example.models.User;

import java.util.List;

public interface Search {

    List<Task> searchTodoByText(String searchText, User user);
}

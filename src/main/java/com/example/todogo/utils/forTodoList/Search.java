package com.example.todogo.utils.forTodoList;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;

import java.util.List;

public interface Search {

    List<Task> searchTodoByText(String searchText, User user);
}

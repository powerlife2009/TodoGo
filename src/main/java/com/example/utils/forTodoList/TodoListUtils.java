package com.example.utils.forTodoList;

import org.springframework.stereotype.Component;

@Component
public class TodoListUtils {

    private final Filter taskFilter;
    private final Sorting taskSorting;
    private final Search taskSearch;

    public TodoListUtils(Filter taskFilter, Sorting taskSorting, Search taskSearch) {
        this.taskFilter = taskFilter;
        this.taskSorting = taskSorting;
        this.taskSearch = taskSearch;
    }

    public Filter getTaskFilter() {
        return taskFilter;
    }

    public Sorting getTaskSorting() {
        return taskSorting;
    }

    public Search getTaskSearch() {
        return taskSearch;
    }
}

package com.example.todogo.services;

import com.example.todogo.models.Task;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class TaskServiceIT {

    @Autowired
    private TaskService taskService;

    @Test
    public void createTask_saveTask() {
        Task task = new Task("new text", new Date(20091989), "Family", 1);
        taskService.saveTask(task);
        Task createdTask = taskService.getTaskById(task.getId());
        assertEquals(task, createdTask);
    }

    @Test
    public void createTask_updateTask() {
        Task task = new Task("new text", new Date(20091989), "Family", 1);
        taskService.saveTask(task);
        task.setText("updated text");
        taskService.saveTask(task);
        Task taskFromDb = taskService.getTaskById(task.getId());
        assertEquals(task, taskFromDb);

    }

    @Test
    public void deleteTask() {
        Task deletingTask = new Task("new task", new Date(20091989), "Home", 3);
        taskService.saveTask(deletingTask);
        taskService.deleteTask(deletingTask.getId());
        Throwable thrown = assertThrows(JpaObjectRetrievalFailureException.class,
                () -> taskService.getTaskById(deletingTask.getId()));
        assertNotNull(thrown.getMessage());
    }
}

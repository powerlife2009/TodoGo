package com.example.todogo.repos;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUser(User user);
}

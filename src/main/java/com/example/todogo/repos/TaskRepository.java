package com.example.todogo.repos;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    List<Task> findAllByUser(User user);

    List<Task> findAllByUser(User user, Sort sort);

    List<Task> findAllByUserAndType(User user, String type);

    List<Task> findAllByUserAndPriority(User user, Integer priority);
}

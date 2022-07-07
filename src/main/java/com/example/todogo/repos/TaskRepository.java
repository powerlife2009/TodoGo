package com.example.todogo.repos;

import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUser(User user, Sort sort);
    List<Task> findAllByUserAndType(User user, String type);
    List<Task> findAllByUserAndPriority(User user, Integer priority);
    List<Task> findAllByUserAndTextContainingIgnoreCase(User user, String searching);
    List<Task> findFirst5ByUserOrderByDateAsc(User user);
}

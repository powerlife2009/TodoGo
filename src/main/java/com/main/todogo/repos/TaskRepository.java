package com.main.todogo.repos;

import com.main.todogo.models.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUserUserId(long userId, Sort sort);
    List<Task> findAllByUserUserIdAndType(long userId, String type);
    List<Task> findAllByUserUserIdAndPriority(long userId, Integer priority);
    List<Task> findAllByUserUserIdAndTextContainingIgnoreCase(long userId, String searching);
    List<Task> findFirst5ByUserUserIdOrderByDateAsc(long userId);
}

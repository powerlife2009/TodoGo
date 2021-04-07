package com.example.repos;

import com.example.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepos extends JpaRepository<Feedback, Long> {
}

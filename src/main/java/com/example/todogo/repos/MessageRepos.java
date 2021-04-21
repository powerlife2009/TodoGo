package com.example.todogo.repos;

import com.example.todogo.models.Message;
import com.example.todogo.models.MessageWay;
import com.example.todogo.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepos extends JpaRepository<Message, Long> {

    List<Message> findAllByMessageWay(MessageWay messageWay);

    List<Message> findAllByUserAndMessageWay(User user, MessageWay messageWay, Sort sort);
}

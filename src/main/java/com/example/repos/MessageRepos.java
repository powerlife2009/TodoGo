package com.example.repos;

import com.example.models.Message;
import com.example.models.MessageWay;
import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepos extends JpaRepository<Message, Long> {

    List<Message> findAllByMessageWay(MessageWay messageWay);

    List<Message> findAllByUserAndMessageWay(User user, MessageWay messageWay);
}

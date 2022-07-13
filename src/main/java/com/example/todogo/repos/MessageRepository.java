package com.example.todogo.repos;

import com.example.todogo.models.Message;
import com.example.todogo.models.MessageWay;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByMessageWay(MessageWay messageWay);
    List<Message> findAllByUserUserIdAndMessageWay(long userId, MessageWay messageWay, Sort sort);
}

package com.example.todogo.services;

import com.example.todogo.models.Message;
import com.example.todogo.models.MessageWay;
import com.example.todogo.models.User;
import com.example.todogo.repos.MessageRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepos messageRepos;

    @Autowired
    public MessageService(MessageRepos messageRepos) {
        this.messageRepos = messageRepos;
    }


    public List<Message> findAllByUserAndMessageWay(User user, MessageWay messageWay) {
        return messageRepos.findAllByUserAndMessageWay(user, messageWay, Sort.by(Sort.Direction.DESC, "messageId"));
    }

    public List<Message> allMessagesByMessageWay(MessageWay messageWay) {
        return messageRepos.findAllByMessageWay(messageWay);
    }

    public Message getMessageById(Long id) {
        return messageRepos.getOne(id);
    }

    public void deleteMessage(Message message) {
        messageRepos.delete(message);
    }

    public void saveMessage(Message message) {
        messageRepos.save(message);
    }
}


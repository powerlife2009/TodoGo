package com.example.services;

import com.example.models.Message;
import com.example.models.MessageWay;
import com.example.models.User;
import com.example.repos.MessageRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepos messageRepos;

    @Autowired
    public MessageService(MessageRepos messageRepos) {
        this.messageRepos = messageRepos;
    }


    public void saveMessage(Message message) {
        messageRepos.save(message);
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

    public List<Message> findAllByUserAndMessageWay(User user, MessageWay messageWay) {
        return messageRepos.findAllByUserAndMessageWay(user, messageWay);
    }
}

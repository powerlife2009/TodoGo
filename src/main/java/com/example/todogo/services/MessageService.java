package com.example.todogo.services;

import com.example.todogo.models.Message;
import com.example.todogo.models.MessageWay;
import com.example.todogo.models.User;
import com.example.todogo.repos.MessageRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public List<Message> getAllMessagesByMessageWay(MessageWay messageWay) {
        List<Message> messageList = messageRepos.findAllByMessageWay(messageWay);
        Collections.reverse(messageList);

        return messageList;
    }

    public Message getMessageById(Long id) {
        return messageRepos.getOne(id);
    }

    public void deleteMessageById(long messageId) {
        messageRepos.deleteById(messageId);
    }

    public void saveNewMessage(Message message, User user, MessageWay messageWay) {
        message.setUser(user);
        message.setMessageWay(messageWay);

        messageRepos.save(message);
    }

    public Message setMessageAsRead(Long id) {
        Message message = getMessageById(id);
        message.setMarkAsRead(true);
        messageRepos.save(message);

        return message;
    }
}


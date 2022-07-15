package com.main.todogo.services;

import com.main.todogo.models.Message;
import com.main.todogo.models.MessageWay;
import com.main.todogo.models.User;
import com.main.todogo.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAllByUserIdAndMessageWay(long userId, MessageWay messageWay) {
        return messageRepository.findAllByUserUserIdAndMessageWay(userId, messageWay, Sort.by(Sort.Direction.DESC, "messageId"));
    }

    public List<Message> getAllMessagesByMessageWay(MessageWay messageWay) {
        List<Message> messageList = messageRepository.findAllByMessageWay(messageWay);
        Collections.reverse(messageList);

        return messageList;
    }

    public Message getMessageById(Long id) {
        return messageRepository.getReferenceById(id);
    }

    public void deleteMessageById(long messageId) {
        messageRepository.deleteById(messageId);
    }

    public void saveNewMessage(Message message, User user, MessageWay messageWay) {
        message.setUser(user);
        message.setMessageWay(messageWay);

        messageRepository.save(message);
    }

    public Message setMessageAsRead(Long id) {
        Message message = getMessageById(id);
        message.setMarkAsRead(true);
        messageRepository.save(message);

        return message;
    }
}
package com.example.todogo.services;

import com.example.todogo.models.Message;
import com.example.todogo.models.MessageWay;
import com.example.todogo.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class MessageServiceIT {

    @Autowired
    private MessageService messageService;

    private final User user = new User("qwerty","qwerty","qwerty@gmail.com");


    @Test
    public void createMessage_saveMessage() {
        Message message = new Message("new subject","Hi, how are you", user);
        messageService.saveMessage(message);
        Message createdMessage = messageService.getMessageById(message.getId());
        assertEquals(message, createdMessage);
    }

    @Test
    public void getAllMessagesByMessageWay() {
        Message messageOne = new Message("new subject","Hi, how are you",MessageWay.INBOX);
        Message messageTwo = new Message("thanks","I am fine", MessageWay.OUTBOX);

        messageService.saveMessage(messageOne);
        messageService.saveMessage(messageTwo);

        List<Message>messagesInbox = messageService.allMessagesByMessageWay(MessageWay.INBOX);
        long quantityOutboxMessages = messagesInbox.stream()
                .filter(message -> message.getMessageWay().equals(MessageWay.OUTBOX))
                .count();
        assertEquals(0, quantityOutboxMessages);

    }


    @Test
    public void deleteMessage() {
        Message deletingMessage = new Message("new subject", "new text", user);
        messageService.saveMessage(deletingMessage);
        messageService.deleteMessage(deletingMessage);

        Throwable thrown = assertThrows(JpaObjectRetrievalFailureException.class,
                () -> messageService.getMessageById(deletingMessage.getId()));
        assertNotNull(thrown.getMessage());
    }
}



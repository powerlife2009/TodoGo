package com.example.utils.forMessages;

import com.example.models.MessageWay;
import com.example.models.User;
import com.example.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagesUtils {

    private final MessageService messageService;

    @Autowired
    public MessagesUtils(MessageService messageService) {
        this.messageService = messageService;
    }

    public int quantityInboxMessages(User user, MessageWay messageWay) {
        long quantity = messageService.findAllByUserAndMessageWay(user, messageWay).stream()
                .filter(message -> !message.getMarkAsRead())
                .count();
        return (int)quantity;
    }
}

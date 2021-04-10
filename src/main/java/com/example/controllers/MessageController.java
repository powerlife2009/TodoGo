package com.example.controllers;

import com.example.models.Message;
import com.example.models.MessageWay;
import com.example.models.User;
import com.example.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/mail")
    public String mailbox(@AuthenticationPrincipal User user,
                          Model model) {
        model.addAttribute("messages", messageService.findAllByUserAndMessageWay(user, MessageWay.INBOX));
        return "user/mail_page";
    }

    @GetMapping("/feedback")
    public String feedback(Model model) {
        model.addAttribute("feedback", new Message());
        return "user/feedback_page";
    }


    @PostMapping("/send_feedback")
    public String sendFeedback(@AuthenticationPrincipal User user,
                               @Valid @ModelAttribute Message message,
                               BindingResult errors) {
        if (errors.hasErrors()) {
            return "user/feedback_page";
        }
        message.setUser(user);
        message.setMessageWay(MessageWay.OUTBOX);
        messageService.saveMessage(message);
        return "redirect:/main";
    }

    @PostMapping("/mail/markAsRead")
    public String markAsRead(@RequestParam Long id) {
        Message message = messageService.getMessageById(id);
        message.setMarkAsRead(true);
        messageService.saveMessage(message);
        return "redirect:/mail";
    }
}

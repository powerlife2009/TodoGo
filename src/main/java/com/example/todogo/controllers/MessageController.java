package com.example.todogo.controllers;

import com.example.todogo.models.Message;
import com.example.todogo.models.MessageWay;
import com.example.todogo.models.User;
import com.example.todogo.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

import static com.example.todogo.constants.TodoGoConstants.*;

@Controller
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/mail")
    public String mailbox(@AuthenticationPrincipal User user, Model model) {
        List<Message> messageInboxList =
                messageService.findAllByUserAndMessageWay(user, MessageWay.INBOX);
        model.addAttribute(MESSAGES, messageInboxList);

        return USER_MAIL_PAGE;
    }

    @GetMapping("/feedback")
    public String feedback(Model model) {
        model.addAttribute(MESSAGE, new Message());

        return USER_FEEDBACK_PAGE;
    }

    @PostMapping("/feedback")
    public String sendFeedback(@AuthenticationPrincipal User user,
                               @Valid @ModelAttribute Message message,
                               BindingResult errors,
                               Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(ACTION_RESULT, HAS_ERRORS);

            return USER_FEEDBACK_PAGE;
        }

        messageService.saveNewMessage(message, user, MessageWay.OUTBOX);
        model.addAttribute(ACTION_RESULT, SUCCESSFULLY);
        model.addAttribute(MESSAGE, new Message());

        return USER_FEEDBACK_PAGE;
    }

    @PostMapping("/mail/{id}/markAsRead")
    public String markAsRead(@PathVariable("id") Long messageId) {
        messageService.setMessageAsRead(messageId);

        return REDIRECT_TO_MAIL;
    }

    @PostMapping("/mail/{id}/remove")
    public String removeMessage(@AuthenticationPrincipal User user, @PathVariable("id") Long id,
            Model model) {
        messageService.deleteMessageById(id);
        model.addAttribute(ACTION_RESULT, SUCCESSFULLY);

        return mailbox(user, model);
    }
}

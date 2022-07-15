package com.main.todogo.controllers;

import com.main.todogo.constants.TodoGoConstants;
import com.main.todogo.models.Message;
import com.main.todogo.models.MessageWay;
import com.main.todogo.models.User;
import com.main.todogo.services.MessageService;
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

@Controller
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/mail")
    public String mailbox(@AuthenticationPrincipal User user, Model model) {
        List<Message> messageInboxList =
                messageService.findAllByUserIdAndMessageWay(user.getUserId(), MessageWay.INBOX);
        model.addAttribute(TodoGoConstants.MESSAGES, messageInboxList);

        return TodoGoConstants.USER_MAIL_PAGE;
    }

    @GetMapping("/feedback")
    public String feedback(Model model) {
        model.addAttribute(TodoGoConstants.MESSAGE, new Message());

        return TodoGoConstants.USER_FEEDBACK_PAGE;
    }

    @PostMapping("/feedback")
    public String sendFeedback(@AuthenticationPrincipal User user,
                               @Valid @ModelAttribute Message message,
                               BindingResult errors,
                               Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.HAS_ERRORS);

            return TodoGoConstants.USER_FEEDBACK_PAGE;
        }

        messageService.saveNewMessage(message, user, MessageWay.OUTBOX);
        model.addAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.SUCCESSFULLY);
        model.addAttribute(TodoGoConstants.MESSAGE, new Message());

        return TodoGoConstants.USER_FEEDBACK_PAGE;
    }

    @PostMapping("/mail/{id}/markAsRead")
    public String markAsRead(@PathVariable("id") Long messageId) {
        messageService.setMessageAsRead(messageId);

        return TodoGoConstants.REDIRECT_TO_MAIL;
    }

    @PostMapping("/mail/{id}/remove")
    public String removeMessage(@AuthenticationPrincipal User user, @PathVariable("id") Long id,
            Model model) {
        messageService.deleteMessageById(id);
        model.addAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.SUCCESSFULLY);

        return mailbox(user, model);
    }
}

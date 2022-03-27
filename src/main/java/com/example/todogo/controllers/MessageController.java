package com.example.todogo.controllers;

import com.example.todogo.constants.TodoGoConstants;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

import static com.example.todogo.constants.TodoGoConstants.*;

@Controller
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;


    @GetMapping("/mail")
    public String mailbox(@AuthenticationPrincipal User user,
                          Model model) {
        List<Message> messageInboxList = messageService.findAllByUserAndMessageWay(user, MessageWay.INBOX);
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
                               RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            return USER_FEEDBACK_PAGE;
        }
        message.setUser(user);
        message.setMessageWay(MessageWay.OUTBOX);
        messageService.saveMessage(message);
        redirectAttributes.addFlashAttribute(MESSAGE, SUCCESSFULLY);
        return REDIRECT_TO_MAIN_PAGE;
    }

    @PostMapping("/mail/markAsRead")
    public String markAsRead(@RequestParam Long id) {
        Message message = messageService.getMessageById(id);
        message.setMarkAsRead(true);
        messageService.saveMessage(message);
        return REDIRECT_TO_MAIL;
    }

    @PostMapping("/mail/remove")
    public String removeMessage(@RequestParam Long id,
                                RedirectAttributes redirectAttributes) {
        Message message = messageService.getMessageById(id);
        messageService.deleteMessage(message);
        redirectAttributes.addFlashAttribute(MESSAGE, SUCCESSFULLY);
        return REDIRECT_TO_MAIL;
    }
}

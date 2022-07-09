package com.example.todogo.controllers;

import com.example.todogo.models.Message;
import com.example.todogo.models.MessageWay;
import com.example.todogo.models.Role;
import com.example.todogo.models.User;
import com.example.todogo.services.MessageService;
import com.example.todogo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

import static com.example.todogo.constants.TodoGoConstants.*;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;
    private final MessageService messageService;

    @GetMapping
    public String adminPage() {
        return ADMIN_PAGE;
    }


    @GetMapping("/users")
    public String usersList(Model model) {
        model.addAttribute(USERS, userService.getAllByRole(Role.ROLE_USER));

        return ADMIN_USERS_LIST;
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable("id") Long userId, Model model) {
        model.addAttribute(USER, userService.getUserById(userId));

        return ADMIN_USER_EDIT;
    }

    @PostMapping("/users/{id}/remove")
    public String removeUser(@PathVariable("id") Long userId,
                             RedirectAttributes redirectAttributes) {
        userService.deleteUserById(userId);
        redirectAttributes.addFlashAttribute(MESSAGE, SUCCESSFULLY);

        return REDIRECT_TO_ADMIN_USERS;
    }

    @GetMapping("/feedbacks")
    public String feedbackList(Model model) {
        List<Message> messageList = messageService.getAllMessagesByMessageWay(MessageWay.OUTBOX);
        model.addAttribute(FEEDBACKS, messageList);

        return ADMIN_FEEDBACK_LIST;
    }

    @GetMapping("/feedbacks/{id}")
    public String showFeedback(@PathVariable("id") Long messageId, Model model) {
        Message feedback = messageService.setMessageAsRead(messageId);

        model.addAttribute(FEEDBACK, feedback);
        model.addAttribute(ANSWER, new Message());

        return ADMIN_READ_FEEDBACK;
    }

    @PostMapping("/feedbacks/{id}/remove")
    public String removeFeedback(@PathVariable("id") Long messageId,
                                 RedirectAttributes redirectAttributes) {
        messageService.deleteMessageById(messageId);
        redirectAttributes.addFlashAttribute(MESSAGE, SUCCESSFULLY);

        return REDIRECT_TO_ADMIN_FEEDBACKS;
    }

    @PostMapping("/feedback/answer")
    public String answerToMessage(@ModelAttribute Message answer,
                                  @RequestParam Long idOwnerFeedback,
                                  RedirectAttributes redirectAttributes) {
        User user = userService.getUserById(idOwnerFeedback);
        messageService.saveNewMessage(answer, user, MessageWay.INBOX);
        redirectAttributes.addFlashAttribute(MESSAGE, SUCCESSFULLY);

        return REDIRECT_TO_ADMIN_FEEDBACKS;
    }
}

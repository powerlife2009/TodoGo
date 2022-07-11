package com.example.todogo.controllers;

import com.example.todogo.constants.TodoGoConstants;
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

import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;
    private final MessageService messageService;

    @GetMapping
    public String adminPage() {
        return TodoGoConstants.ADMIN_PAGE;
    }

    @GetMapping("/users")
    public String usersList(Model model) {
        model.addAttribute(TodoGoConstants.USERS, userService.getAllByRole(Role.ROLE_USER));

        return TodoGoConstants.ADMIN_USERS_LIST;
    }

    @GetMapping("/users/{userId}")
    public String showUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute(TodoGoConstants.USER, userService.getUserById(userId));

        return TodoGoConstants.ADMIN_USER_EDIT;
    }

    @PostMapping("/users/{userId}/remove")
    public String removeUser(@PathVariable("userId") Long userId,
                             Model model) {
        userService.deleteUserById(userId);
        model.addAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.SUCCESSFULLY);

        return usersList(model);
    }

    @GetMapping("/feedbacks")
    public String feedbackList(Model model) {
        List<Message> messageList = messageService.getAllMessagesByMessageWay(MessageWay.OUTBOX);
        model.addAttribute(TodoGoConstants.FEEDBACKS, messageList);

        return TodoGoConstants.ADMIN_FEEDBACK_LIST;
    }

    @GetMapping("/feedbacks/{feedbackId}")
    public String showFeedback(@PathVariable("feedbackId") Long feedbackId, Model model) {
        Message feedback = messageService.setMessageAsRead(feedbackId);

        model.addAttribute(TodoGoConstants.FEEDBACK, feedback);
        model.addAttribute(TodoGoConstants.ANSWER, new Message());

        return TodoGoConstants.ADMIN_READ_FEEDBACK;
    }

    @PostMapping("/feedbacks/{feedbackId}/remove")
    public String removeFeedback(@PathVariable("feedbackId") Long feedbackId, Model model) {
        messageService.deleteMessageById(feedbackId);
        model.addAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.SUCCESSFULLY);

        return feedbackList(model);
    }

    @PostMapping("/feedback/answer")
    public String answerToMessage(@ModelAttribute Message answer,
                                  @RequestParam Long idOwnerFeedback,
                                  Model model) {
        User user = userService.getUserById(idOwnerFeedback);
        messageService.saveNewMessage(answer, user, MessageWay.INBOX);
        model.addAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.SUCCESSFULLY);

        return feedbackList(model);
    }
}

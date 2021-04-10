package com.example.controllers;

import com.example.models.Message;
import com.example.models.MessageWay;
import com.example.models.Role;
import com.example.models.User;
import com.example.services.MessageService;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public AdminController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }


    @GetMapping
    public String adminPage() {
        return "admin/admin_page";
    }


    @GetMapping("/users")
    public String usersList(Model model) {
        model.addAttribute("users", userService.findAllUsers(Role.ROLE_USER));
        return "admin/users_list";
    }


    @GetMapping("/users/{id}")
    public String showUser(@PathVariable("id") Long id,
                           Model model) {
        model.addAttribute("user", userService.findByUserId(id));
        return "admin/user_edit";
    }

    @PostMapping("/users/remove")
    public String removeUser(@RequestParam Long id) {
        User user = userService.findByUserId(id);
        userService.deleteUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/feedbacks")
    public String feedbackList(Model model) {
        model.addAttribute("feedbacks", messageService.allMessagesByMessageWay(MessageWay.OUTBOX));
        return "admin/feedback_list";
    }

    @GetMapping("/feedbacks/{id}")
    public String showFeedback(@PathVariable("id") Long id,
                               Model model) {
        Message feedback = messageService.getMessageById(id);
        feedback.setMarkAsRead(true);
        model.addAttribute("feedback", feedback);
        model.addAttribute("answer", new Message());
        return "admin/read_feedback";
    }

    @PostMapping("/feedbacks/remove")
    public String removeMessage(@RequestParam Long id) {
        Message feedback = messageService.getMessageById(id);
        messageService.deleteMessage(feedback);
        return "redirect:/admin/feedbacks";
    }

    @PostMapping("/feedback/answer")
    public String answerToMessage(@ModelAttribute Message answer,
                                  @RequestParam Long idOwnerFeedback) {
        User user = userService.findByUserId(idOwnerFeedback);
        answer.setUser(user);
        answer.setMessageWay(MessageWay.INBOX);
        messageService.saveMessage(answer);
        return "redirect:/admin/feedbacks";
    }
}

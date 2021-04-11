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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

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
    public String removeUser(@RequestParam Long id,
                             RedirectAttributes redirectAttributes) {
        User user = userService.findByUserId(id);
        userService.deleteUser(user);
        redirectAttributes.addFlashAttribute("message", "successfully");
        return "redirect:/admin/users";
    }

    @GetMapping("/feedbacks")
    public String feedbackList(Model model) {
        List<Message> messageList = messageService.allMessagesByMessageWay(MessageWay.OUTBOX);
        Collections.reverse(messageList);
        model.addAttribute("feedbacks", messageList);
        return "admin/feedback_list";
    }

    @GetMapping("/feedbacks/{id}")
    public String showFeedback(@PathVariable("id") Long id,
                               Model model) {
        Message feedback = messageService.getMessageById(id);
        feedback.setMarkAsRead(true);
        messageService.saveMessage(feedback);
        model.addAttribute("feedback", feedback);
        model.addAttribute("answer", new Message());
        return "admin/read_feedback";
    }

    @PostMapping("/feedbacks/remove")
    public String removeMessage(@RequestParam Long id,
                                RedirectAttributes redirectAttributes) {
        Message feedback = messageService.getMessageById(id);
        messageService.deleteMessage(feedback);
        redirectAttributes.addFlashAttribute("message", "successfully");
        return "redirect:/admin/feedbacks";
    }

    @PostMapping("/feedback/answer")
    public String answerToMessage(@ModelAttribute Message answer,
                                  @RequestParam Long idOwnerFeedback,
                                  RedirectAttributes redirectAttributes) {
        User user = userService.findByUserId(idOwnerFeedback);
        answer.setUser(user);
        answer.setMessageWay(MessageWay.INBOX);
        messageService.saveMessage(answer);
        redirectAttributes.addFlashAttribute("message", "successfully");
        return "redirect:/admin/feedbacks";
    }
}

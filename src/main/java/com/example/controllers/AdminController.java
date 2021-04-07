package com.example.controllers;

import com.example.services.FeedBackService;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final FeedBackService feedBackService;

    @Autowired
    public AdminController(UserService userService, FeedBackService feedBackService) {
        this.userService = userService;
        this.feedBackService = feedBackService;
    }


    @GetMapping
    public String adminPage() {
        return "admin_page";
    }


    @GetMapping("/allUsers")
    public String usersList(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users_list";
    }

    @GetMapping("/feedbacks")
    public String feedbackList(Model model) {
        model.addAttribute("feedbacks",feedBackService.allFeedbacks());
        return "feedback_list";
    }
}

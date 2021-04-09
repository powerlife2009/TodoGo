package com.example.controllers;

import com.example.models.Task;
import com.example.models.User;
import com.example.services.FeedBackService;
import com.example.services.TaskService;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "admin/admin_page";
    }


    @GetMapping("/users")
    public String usersList(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/users_list";
    }

    @GetMapping("/feedbacks")
    public String feedbackList(Model model) {
        model.addAttribute("feedbacks",feedBackService.allFeedbacks());
        return "admin/feedback_list";
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable("id") Long id,
                           Model model) {
        model.addAttribute("user", userService.findByUserId(id));
        return "admin/user_edit";
    }

    @PostMapping("/users/remove")
    public String removeUser (@RequestParam Long id) {
        User user = userService.findByUserId(id);
        userService.deleteUser(user);
        return "redirect:/admin/users";
    }
}

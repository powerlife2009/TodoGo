package com.example.controllers;

import com.example.models.Feedback;
import com.example.models.User;
import com.example.services.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class FeedBackController {

    private final FeedBackService feedBackService;

    @Autowired
    public FeedBackController(FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    @GetMapping("/feedback")
    public String feedback(Model model) {
        model.addAttribute("feedback", new Feedback());
        return "feedback";
    }


    @PostMapping("/send_feedback")
    public String sendFeedback(@AuthenticationPrincipal User user,
                               @Valid @ModelAttribute Feedback feedback,
                               BindingResult errors,
                               Model model) {
        if (errors.hasErrors()) {
            return "feedback";
        }
        feedback.setUser(user);
        System.out.println(feedback.getUser().getEmail());
        System.out.println(feedback.getFromName());
        System.out.println(feedback.getText());
        feedBackService.saveFeedBack(feedback);
        return "redirect:/main";
    }
}

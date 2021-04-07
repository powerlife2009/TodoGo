package com.example.services;

import com.example.models.Feedback;
import com.example.repos.FeedBackRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackService {

    private final FeedBackRepos feedBackRepos;

    @Autowired
    public FeedBackService(FeedBackRepos feedBackRepos) {
        this.feedBackRepos = feedBackRepos;
    }


    public void saveFeedBack(Feedback feedback) {
        feedBackRepos.save(feedback);
    }

    public List<Feedback>allFeedbacks() {
        return feedBackRepos.findAll();
    }
}

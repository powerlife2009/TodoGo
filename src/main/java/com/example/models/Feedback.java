package com.example.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please enter your name")
    private String fromName;

    @NotBlank(message = "Please enter text")
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Feedback() {
    }

    public Feedback(String fromName, String text, User user) {
        this.fromName = fromName;
        this.text = text;
        this.user = user;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String shortText() {
        if (text.length() <= 20) {
            return text;
        } else {
            return text.substring(0, 20) + " ...";
        }
    }
}

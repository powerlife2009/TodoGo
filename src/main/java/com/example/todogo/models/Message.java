package com.example.todogo.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please enter message subject")
    private String subject;

    @NotBlank(message = "Please enter text")
    private String text;

    private Boolean markAsRead = false;

    @ManyToOne()
    private User user;

    @Enumerated(EnumType.STRING)
    private MessageWay messageWay;

    public Message() {
    }

    public Message(String subject, String text, MessageWay messageWay) {
        this.subject = subject;
        this.text = text;
        this.messageWay = messageWay;
    }

    public Message(String subject, String text, User user) {
        this.subject = subject;
        this.text = text;
        this.user = user;
    }

    public Boolean getMarkAsRead() {
        return markAsRead;
    }

    public void setMarkAsRead(Boolean markAsRead) {
        this.markAsRead = markAsRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public MessageWay getMessageWay() {
        return messageWay;
    }

    public void setMessageWay(MessageWay messageWay) {
        this.messageWay = messageWay;
    }

    public String shortText() {
        return text.length() <= 20 ? text : text.substring(0, 20) + "...";
    }
}

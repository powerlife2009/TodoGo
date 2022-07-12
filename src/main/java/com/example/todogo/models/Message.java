package com.example.todogo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;

    @NotBlank(message = "Please enter text")
    private String text;

    private boolean markAsRead = false;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private MessageWay messageWay;

    public Message(String text, MessageWay messageWay) {
        this.text = text;
        this.messageWay = messageWay;
    }

    public Message(String text, User user) {
        this.text = text;
        this.user = user;
    }

    public String shortText() {
        return text.length() <= 20 ? text : text.substring(0, 20) + "...";
    }
}

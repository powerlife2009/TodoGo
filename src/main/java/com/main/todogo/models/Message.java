package com.main.todogo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;

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

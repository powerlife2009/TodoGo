package com.example.todogo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long passwordId;

    @NotBlank
    private String domain;

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @ManyToOne
    private User user;
}

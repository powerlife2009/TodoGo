package com.main.todogo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

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

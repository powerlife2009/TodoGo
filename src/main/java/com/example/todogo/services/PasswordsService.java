package com.example.todogo.services;

import com.example.todogo.repos.PasswordsRepository;
import org.springframework.stereotype.Service;

@Service
public class PasswordsService {

    private final PasswordsRepository passwordsRepository;

    public PasswordsService(PasswordsRepository passwordsRepository) {
        this.passwordsRepository = passwordsRepository;
    }
}

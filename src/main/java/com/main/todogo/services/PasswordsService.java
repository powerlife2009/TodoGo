package com.main.todogo.services;

import com.main.todogo.repos.PasswordsRepository;
import org.springframework.stereotype.Service;

@Service
public class PasswordsService {

    private final PasswordsRepository passwordsRepository;

    public PasswordsService(PasswordsRepository passwordsRepository) {
        this.passwordsRepository = passwordsRepository;
    }
}

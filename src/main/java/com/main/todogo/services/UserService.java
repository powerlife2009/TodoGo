package com.main.todogo.services;

import com.main.todogo.models.Role;
import com.main.todogo.models.User;
import com.main.todogo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with user name %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username));
    }

    public boolean saveUser(User user) {
        Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb.isPresent()) {
            return false;
        }

        user.setRole(Role.ROLE_USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public List<User> getAllByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public void deleteUserById(long userId) {
        userRepository.deleteById(userId);
    }

    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
    }
}

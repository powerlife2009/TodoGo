package com.example.todogo.services;

import com.example.todogo.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Test
    public void loadUserByUserName() throws UsernameNotFoundException {
        User user = new User("Kurgan", "kurgan", "kurgan@kurgan.ru");
        userService.saveUser(user);
        UserDetails userDetails = userService.loadUserByUsername("Kurgan");
        assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    public void saveUser() {
        User userOne = new User("Ivan", "Ivanov", "ivanov@ivan.ru");
        userService.saveUser(userOne);
        User userFromDb = userService.findByUserId(userOne.getId());
        assertEquals(userOne, userFromDb);
    }

    @Test
    public void deleteUser() {
        User deletingUser = new User("newUser", "HomeHome", "home@home.ru");
        userService.saveUser(deletingUser);
        userService.deleteUser(deletingUser);
        Throwable thrown = assertThrows(JpaObjectRetrievalFailureException.class,
                () -> userService.findByUserId(deletingUser.getId()));
        assertNotNull(thrown.getMessage());
    }
}

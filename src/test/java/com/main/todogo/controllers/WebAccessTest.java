package com.main.todogo.controllers;

import com.main.todogo.config.security.PasswordEncoder;
import com.main.todogo.config.security.SecurityConfig;
import com.main.todogo.models.User;
import com.main.todogo.services.MessageService;
import com.main.todogo.services.NoteService;
import com.main.todogo.services.TaskService;
import com.main.todogo.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {UserController.class, AdminController.class, MainController.class})
@Import({SecurityConfig.class, PasswordEncoder.class})
class WebAccessTest {
    @Autowired MockMvc mvc;
    @MockitoBean UserService users;
    @MockitoBean MessageService messages;
    @MockitoBean TaskService tasks;
    @MockitoBean NoteService notes;

    @Test
    void publicPagesAreAccessibleWithoutAuthentication() throws Exception {
        mvc.perform(get("/registration")).andExpect(status().isOk())
                .andExpect(view().name("logIn/registration"));
        mvc.perform(get("/login")).andExpect(status().isOk());
    }

    @Test
    void privatePagesRedirectAnonymousVisitorsToLogin() throws Exception {
        mvc.perform(get("/admin")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
        mvc.perform(get("/myTasks")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void userCannotOpenAdminPageButAdminCan() throws Exception {
        mvc.perform(get("/admin").with(user("alice").roles("USER")))
                .andExpect(status().isForbidden());
        mvc.perform(get("/admin").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk()).andExpect(view().name("admin/admin_page"));
    }

    @Test
    void homePageBelongsToUserRole() throws Exception {
        mvc.perform(get("/home").with(user("alice").roles("USER")))
                .andExpect(status().isOk()).andExpect(view().name("user/home_page"));
        mvc.perform(get("/home").with(user("admin").roles("ADMIN")))
                .andExpect(status().isForbidden());
    }

    @Test
    void validRegistrationRedirectsToLogin() throws Exception {
        when(users.saveUser(any(User.class))).thenReturn(true);

        mvc.perform(post("/registration").param("username", "alice")
                        .param("password", "secret12").param("email", "alice@example.com"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
        verify(users).saveUser(any(User.class));
    }

    @Test
    void invalidRegistrationRemainsOnFormWithoutSaving() throws Exception {
        mvc.perform(post("/registration").param("username", "a")
                        .param("password", "short").param("email", "invalid"))
                .andExpect(status().isOk()).andExpect(view().name("logIn/registration"))
                .andExpect(model().attributeHasFieldErrors("user", "username", "password", "email"));
    }

    @Test
    void duplicateRegistrationShowsError() throws Exception {
        when(users.saveUser(any(User.class))).thenReturn(false);

        mvc.perform(post("/registration").param("username", "alice")
                        .param("password", "secret12").param("email", "alice@example.com"))
                .andExpect(status().isOk()).andExpect(view().name("logIn/registration"))
                .andExpect(model().attributeExists("messageExist"));
    }
}

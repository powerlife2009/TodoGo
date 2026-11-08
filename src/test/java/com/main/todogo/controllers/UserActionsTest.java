package com.main.todogo.controllers;

import com.main.todogo.config.security.PasswordEncoder;
import com.main.todogo.config.security.SecurityConfig;
import com.main.todogo.models.*;
import com.main.todogo.services.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {TasksController.class, NoteController.class, MessageController.class})
@Import({SecurityConfig.class, PasswordEncoder.class})
class UserActionsTest {
    @Autowired MockMvc mvc;
    @MockitoBean UserService users;
    @MockitoBean TaskService tasks;
    @MockitoBean NoteService notes;
    @MockitoBean MessageService messages;

    private User alice() {
        User user = new User("alice", "hash", "alice@example.com");
        user.setUserId(7L);
        user.setRole(Role.ROLE_USER);
        return user;
    }

    @Test
    void validTaskIsSavedForAuthenticatedUser() throws Exception {
        User owner = alice();
        mvc.perform(post("/myTasks/save").with(user(owner))
                        .param("text", "Buy milk").param("date", "2026-11-09")
                        .param("priority", "2").param("type", "HOME"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/myTasks"))
                .andExpect(flash().attribute("action_result", "successfully"));

        ArgumentCaptor<Task> saved = ArgumentCaptor.forClass(Task.class);
        verify(tasks).saveTask(saved.capture(), same(owner));
        assertEquals("Buy milk", saved.getValue().getText());
    }

    @Test
    void invalidTaskIsNotSaved() throws Exception {
        mvc.perform(post("/myTasks/save").with(user(alice()))
                        .param("text", " ").param("priority", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("action_result", "has errors"));
        verify(tasks, never()).saveTask(any(), any());
    }

    @Test
    void validNoteIsSavedForAuthenticatedUser() throws Exception {
        User owner = alice();
        mvc.perform(post("/myNotes/create").with(user(owner))
                        .param("title", "Shopping").param("text", "Buy milk"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/myNotes"));
        verify(notes).saveNote(any(Note.class), same(owner));
    }

    @Test
    void emptyNoteIsRejected() throws Exception {
        mvc.perform(post("/myNotes/create").with(user(alice()))
                        .param("title", " ").param("text", " "))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("action_result", "has errors"));
        verify(notes, never()).saveNote(any(), any());
    }

    @Test
    void feedbackGoesToOutbox() throws Exception {
        User owner = alice();
        mvc.perform(post("/feedback").with(user(owner)).param("text", "Question"))
                .andExpect(status().isOk()).andExpect(view().name("user/feedback_page"));
        verify(messages).saveNewMessage(any(Message.class), same(owner), eq(MessageWay.OUTBOX));
    }
}

package com.main.todogo.repos;

import com.main.todogo.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
class RepositoryQueriesTest {
    @Autowired UserRepository users;
    @Autowired TaskRepository tasks;
    @Autowired NoteRepository notes;
    @Autowired MessageRepository messages;

    @Test
    void taskQueriesFilterByOwnerAndSearchCaseInsensitively() {
        User alice = user("alice");
        User bob = user("bob");
        Task first = task(alice, "Buy Milk", "HOME", 2, 1000L);
        Task second = task(alice, "Read book", "WORK", 1, 2000L);
        task(bob, "Buy milk", "HOME", 2, 500L);

        assertEquals(2, tasks.findAllByUserUserId(alice.getUserId(), Sort.by("date")).size());
        assertEquals(first.getTaskId(), tasks.findAllByUserUserIdAndTextContainingIgnoreCase(
                alice.getUserId(), "milk").get(0).getTaskId());
        assertEquals(first.getTaskId(), tasks.findAllByUserUserIdAndType(
                alice.getUserId(), "HOME").get(0).getTaskId());
        assertEquals(second.getTaskId(), tasks.findAllByUserUserIdAndPriority(
                alice.getUserId(), 1).get(0).getTaskId());
        assertEquals(2, tasks.findFirst5ByUserUserIdOrderByDateAsc(alice.getUserId()).size());
        assertEquals(first.getTaskId(), tasks.findFirst5ByUserUserIdOrderByDateAsc(
                alice.getUserId()).get(0).getTaskId());
    }

    @Test
    void upcomingTasksAreLimitedToFive() {
        User user = user("alice");
        for (int i = 0; i < 7; i++) task(user, "Task " + i, "WORK", 1, i * 1000L);

        assertEquals(5, tasks.findFirst5ByUserUserIdOrderByDateAsc(user.getUserId()).size());
    }

    @Test
    void notesAndMessagesStayScopedToTheirOwnerAndMailbox() {
        User alice = user("alice");
        User bob = user("bob");
        Note note = new Note();
        note.setTitle("Title");
        note.setText("Long note text");
        note.setUser(alice);
        notes.saveAndFlush(note);
        Message inbox = message(alice, MessageWay.INBOX);
        message(alice, MessageWay.OUTBOX);
        message(bob, MessageWay.INBOX);

        assertEquals(1, notes.findAllByUserUserId(alice.getUserId()).size());
        assertTrue(notes.findAllByUserUserId(bob.getUserId()).isEmpty());
        assertEquals(inbox.getMessageId(), messages.findAllByUserUserIdAndMessageWay(
                alice.getUserId(), MessageWay.INBOX, Sort.by(Sort.Direction.DESC, "messageId"))
                .get(0).getMessageId());
        assertEquals(1, messages.findAllByMessageWay(MessageWay.OUTBOX).size());
    }

    private User user(String username) {
        User user = new User(username, "secret12", username + "@example.com");
        user.setRole(Role.ROLE_USER);
        return users.saveAndFlush(user);
    }

    private Task task(User user, String text, String type, int priority, long date) {
        Task task = new Task(text, new Date(date), type, priority);
        task.setUser(user);
        return tasks.saveAndFlush(task);
    }

    private Message message(User user, MessageWay way) {
        Message message = new Message("Hello", way);
        message.setUser(user);
        return messages.saveAndFlush(message);
    }
}

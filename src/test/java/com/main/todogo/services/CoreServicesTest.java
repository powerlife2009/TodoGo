package com.main.todogo.services;

import com.main.todogo.models.*;
import com.main.todogo.repos.*;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CoreServicesTest {
    @Test
    void registrationAssignsUserRoleAndHashesPassword() {
        UserRepository repository = mock(UserRepository.class);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserService service = new UserService(repository, encoder);
        User user = new User("alice", "secret12", "alice@example.com");

        assertTrue(service.saveUser(user));
        assertEquals(Role.ROLE_USER, user.getRole());
        assertNotEquals("secret12", user.getPassword());
        assertTrue(encoder.matches("secret12", user.getPassword()));
        verify(repository).save(user);
    }

    @Test
    void duplicateUsernameIsRejectedWithoutChangingPassword() {
        UserRepository repository = mock(UserRepository.class);
        when(repository.findByUsername("alice")).thenReturn(Optional.of(new User()));
        User user = new User("alice", "secret12", "alice@example.com");

        assertFalse(new UserService(repository, new BCryptPasswordEncoder()).saveUser(user));
        assertEquals("secret12", user.getPassword());
        verify(repository, never()).save(any());
    }

    @Test
    void authenticationLoadsExistingUserAndRejectsUnknownUser() {
        UserRepository repository = mock(UserRepository.class);
        User user = new User("alice", "hash", "alice@example.com");
        when(repository.findByUsername("alice")).thenReturn(Optional.of(user));
        UserService service = new UserService(repository, new BCryptPasswordEncoder());

        assertSame(user, service.loadUserByUsername("alice"));
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("missing"));
    }

    @Test
    void taskSortingUsesDescendingPriorityAndAscendingOtherFields() {
        TaskRepository repository = mock(TaskRepository.class);
        TaskService service = new TaskService(repository);

        service.getAllTaskSortedBy(7, "priority");
        service.getAllTaskSortedBy(7, "date");
        verify(repository).findAllByUserUserId(7, Sort.by(Sort.Direction.DESC, "priority"));
        verify(repository).findAllByUserUserId(7, Sort.by("date"));
    }

    @Test
    void taskQueueAndUpcomingTasksAreScopedToUser() {
        TaskRepository repository = mock(TaskRepository.class);
        TaskService service = new TaskService(repository);

        service.sortTasksAsQueue(7);
        service.getTasksSortedByDateAndNearestFive(7);
        verify(repository).findAllByUserUserId(7, Sort.by(Sort.Direction.DESC, "taskId"));
        verify(repository).findFirst5ByUserUserIdOrderByDateAsc(7);
    }

    @Test
    void savingTaskAssociatesOwner() {
        TaskRepository repository = mock(TaskRepository.class);
        Task task = new Task("read", new Date(), "WORK", 2);
        User owner = new User();

        new TaskService(repository).saveTask(task, owner);
        assertSame(owner, task.getUser());
        verify(repository).save(task);
    }

    @Test
    void noteSearchOnlyReturnsMatchingOwnersNotes() {
        NoteRepository repository = mock(NoteRepository.class);
        Note matching = new Note();
        matching.setText("Buy milk");
        Note other = new Note();
        other.setText("Read book");
        when(repository.findAllByUserUserId(7)).thenReturn(List.of(matching, other));

        assertEquals(List.of(matching), new NoteService(repository).searchNoteByText(7, "milk"));
        verify(repository).findAllByUserUserId(7);
    }

    @Test
    void savingNoteSetsOwnerAndCreationDate() {
        NoteRepository repository = mock(NoteRepository.class);
        Note note = new Note();
        User owner = new User();
        long before = System.currentTimeMillis();

        new NoteService(repository).saveNote(note, owner);
        assertSame(owner, note.getUser());
        assertTrue(note.getCreatedDate().getTime() >= before);
        verify(repository).save(note);
    }

    @Test
    void inboxIsRequestedForOwnerInNewestFirstOrder() {
        MessageRepository repository = mock(MessageRepository.class);
        new MessageService(repository).findAllByUserIdAndMessageWay(7, MessageWay.INBOX);

        verify(repository).findAllByUserUserIdAndMessageWay(7, MessageWay.INBOX,
                Sort.by(Sort.Direction.DESC, "messageId"));
    }

    @Test
    void markingMessageReadPersistsTheChange() {
        MessageRepository repository = mock(MessageRepository.class);
        Message message = new Message("Hello", MessageWay.INBOX);
        when(repository.getReferenceById(12L)).thenReturn(message);

        assertSame(message, new MessageService(repository).setMessageAsRead(12L));
        assertTrue(message.isMarkAsRead());
        verify(repository).save(message);
    }

    @Test
    void savingMessageSetsOwnerAndMailbox() {
        MessageRepository repository = mock(MessageRepository.class);
        Message message = new Message();
        User owner = new User();

        new MessageService(repository).saveNewMessage(message, owner, MessageWay.OUTBOX);
        assertSame(owner, message.getUser());
        assertEquals(MessageWay.OUTBOX, message.getMessageWay());
        verify(repository).save(message);
    }

    @Test
    void adminFeedbackIsPresentedNewestFirst() {
        MessageRepository repository = mock(MessageRepository.class);
        Message older = new Message();
        Message newer = new Message();
        when(repository.findAllByMessageWay(MessageWay.OUTBOX))
                .thenReturn(new ArrayList<>(List.of(older, newer)));

        assertEquals(List.of(newer, older),
                new MessageService(repository).getAllMessagesByMessageWay(MessageWay.OUTBOX));
    }
}

package com.example.todogo.services;

import com.example.todogo.models.Note;
import com.example.todogo.models.User;
import com.example.todogo.repos.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void saveNote(Note note, User user) {
        note.setUser(user);
        note.setCreatedDate(new Date());

        noteRepository.save(note);
    }

    public List<Note> getNotesByUser(User user) {
        List<Note> allByUser = noteRepository.findAllByUser(user);
        Collections.reverse(allByUser);

        return allByUser;
    }

    public Note getNoteById(long noteId) {
        return noteRepository.getOne(noteId);
    }
}

package com.example.todogo.services;

import com.example.todogo.models.Note;
import com.example.todogo.models.User;
import com.example.todogo.repos.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}

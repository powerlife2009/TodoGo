package com.example.todogo.repos;

import com.example.todogo.models.Note;
import com.example.todogo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByUser(User user);
}

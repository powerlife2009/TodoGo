package com.example.todogo.controllers;

import com.example.todogo.constants.TodoGoConstants;
import com.example.todogo.models.Note;
import com.example.todogo.models.User;
import com.example.todogo.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/myNotes")
    public String toNotesPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(TodoGoConstants.NEW_NOTE, new Note());
        model.addAttribute(TodoGoConstants.NOTE_LIST, noteService.getNotesByUser(user));

        return TodoGoConstants.USER_NOTES_PAGE;
    }

    @GetMapping("/myNotes/{noteId}/read")
    public String readNote(@PathVariable("noteId") long noteId, Model model) {
        model.addAttribute("note", noteService.getNoteById(noteId));

        return "user/read_note";
    }

    @PostMapping("/myNotes/create")
    public String createNote(@Valid @ModelAttribute Note newNote,
            BindingResult errors,
            @AuthenticationPrincipal User user,
            RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.HAS_ERRORS);
        } else {
            noteService.saveNote(newNote, user);
            redirectAttributes.addFlashAttribute(TodoGoConstants.ACTION_RESULT, TodoGoConstants.SUCCESSFULLY);
        }

        return TodoGoConstants.REDIRECT_TO_NOTES_PAGE;
    }
}

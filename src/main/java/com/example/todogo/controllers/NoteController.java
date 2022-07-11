package com.example.todogo.controllers;

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

import static com.example.todogo.constants.TodoGoConstants.ACTION_RESULT;
import static com.example.todogo.constants.TodoGoConstants.HAS_ERRORS;
import static com.example.todogo.constants.TodoGoConstants.REDIRECT_TO_NOTES_PAGE;
import static com.example.todogo.constants.TodoGoConstants.SUCCESSFULLY;

@Controller
@AllArgsConstructor
public class NoteController {

    private final NoteService noteService;

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
            redirectAttributes.addFlashAttribute(ACTION_RESULT, HAS_ERRORS);
        } else {
            noteService.saveNote(newNote, user);
            redirectAttributes.addFlashAttribute(ACTION_RESULT, SUCCESSFULLY);
        }

        return REDIRECT_TO_NOTES_PAGE;
    }
}

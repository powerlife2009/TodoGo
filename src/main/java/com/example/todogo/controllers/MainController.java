package com.example.todogo.controllers;

import com.example.todogo.constants.TodoGoConstants;
import com.example.todogo.models.Note;
import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.services.NoteService;
import com.example.todogo.services.TaskService;
import com.example.todogo.util.TodoGoUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class MainController {

    private TaskService taskService;

    private NoteService noteService;

    @GetMapping
    public String startPage(@AuthenticationPrincipal User user, Model model) {
        if (Objects.nonNull(user)) {
            if (user.isAdmin()) {
                return TodoGoConstants.ADMIN_PAGE;
            } else if (user.isUser()) {
                return toHomePage(model);
            }
        }

        return TodoGoConstants.GUEST_PAGE;
    }

    @GetMapping("/home")
    public String toHomePage(Model model) {
        model.addAttribute(TodoGoConstants.DATE_TODAY, TodoGoUtils.getDateToday());

        return TodoGoConstants.USER_HOME_PAGE;
    }

    @PostMapping("/global-search")
    public String globalSearch(@AuthenticationPrincipal User user,
            @RequestParam String searchText, Model model) {
        List<Task> tasks = taskService.searchTaskByText(user.getUserId(), searchText);
        List<Note> notes = noteService.searchNoteByText(user.getUserId(), searchText);

        model.addAttribute("foundTasks", tasks);
        model.addAttribute("foundNotes", notes);

        return TodoGoConstants.GLOBAL_SEARCH_PAGE;
    }
}

package com.example.todogo.controllers;

import com.example.todogo.models.Groups;
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
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

import static com.example.todogo.constants.TodoGoConstants.*;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class MainController {

    private final TaskService taskService;

    private final NoteService noteService;

    @GetMapping
    public String startPage(@AuthenticationPrincipal User user, Model model) {
        if (Objects.nonNull(user)) {
            if (user.isAdmin()) {
                return ADMIN_PAGE;
            } else if (user.isUser()) {
                return toHomePage(model);
            }
        }

        return GUEST_PAGE;
    }

    @GetMapping("/myTasks")
    public String toMyTasksPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(NEW_TASK, new Task());
        model.addAttribute(GROUPS, Groups.values());
        model.addAttribute(NEAREST, taskService.getTasksSortedByDateAndNearestFive(user));
        model.addAttribute(TODO_LIST, taskService.sortTasksAsQueue(user));

        return USER_TASKS_PAGE;
    }

    @GetMapping("/home")
    public String toHomePage(Model model) {
        model.addAttribute(DATE_TODAY, TodoGoUtils.getDateToday());

        return USER_HOME_PAGE;
    }

    @GetMapping("/myCalendar")
    public String toCalendarPage() {
        return USER_CALENDAR_PAGE;
    }

    @GetMapping("/myNotes")
    public String toNotesPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(NEW_NOTE, new Note());
        model.addAttribute(NOTE_LIST, noteService.getNotesByUser(user));

        return USER_NOTES_PAGE;
    }
}

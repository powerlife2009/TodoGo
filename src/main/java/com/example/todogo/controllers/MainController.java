package com.example.todogo.controllers;

import com.example.todogo.models.Groups;
import com.example.todogo.models.Role;
import com.example.todogo.models.Task;
import com.example.todogo.models.User;
import com.example.todogo.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static com.example.todogo.constants.TodoGoConstants.*;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class MainController {

    private final TaskService taskService;

    @GetMapping
    public String startPage() {
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
    public String toHomePage(@AuthenticationPrincipal User user, Model model) {
        if (user.getRole().equals(Role.ROLE_ADMIN)) {
            return ADMIN_PAGE;
        }

        LocalDate localDate = LocalDate.now();
        String formattedDate = localDate.format(DateTimeFormatter
                .ofLocalizedDate(FormatStyle.FULL));

        model.addAttribute("dateToday", formattedDate);

        return USER_HOME_PAGE;
    }

    @GetMapping("/myCalendar")
    public String toCalendarPage() {

        return USER_CALENDAR_PAGE;
    }

    @GetMapping("/myNotes")
    public String toNotesPage() {

        return USER_NOTES_PAGE;
    }
}
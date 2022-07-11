package com.example.todogo.controllers;

import com.example.todogo.constants.TodoGoConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class CalendarController {

    @GetMapping("/myCalendar")
    public String toCalendarPage() {
        return TodoGoConstants.USER_CALENDAR_PAGE;
    }
}

package com.example.todogo.controllers;

import com.example.todogo.constants.TodoGoConstants;
import com.example.todogo.services.CalendarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myCalendar")
@AllArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping
    public String toCalendarPage(Model model) {
        model.addAttribute(TodoGoConstants.CURRENT_DAY, calendarService.getCurrentDayOfMonth());
        model.addAttribute(TodoGoConstants.MONTH_NAME, calendarService.getNameOfCurrentMonth());
        model.addAttribute(TodoGoConstants.CURRENT_YEAR, calendarService.getCurrentYear());
        model.addAttribute(TodoGoConstants.DAYS_OF_MONTH, calendarService.getNumberOfDaysOfTheCurrentMonth());
        model.addAttribute(TodoGoConstants.FIRST_DAY_OF_WEEK, calendarService.getFirstDateOfWeek());

        return TodoGoConstants.USER_CALENDAR_PAGE;
    }
}

package com.example.todogo.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

@Service
public class CalendarService {

    private final Calendar calendar;

    public CalendarService() {
        this.calendar = Calendar.getInstance();
    }

    public int getNumberOfDaysOfTheCurrentMonth() {
        YearMonth yearMonth = YearMonth.of(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1);

        return yearMonth.lengthOfMonth();
    }

    public int getCurrentDayOfMonth() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getNameOfCurrentMonth() {
        Month month = Month.of(calendar.get(Calendar.MONTH) + 1);

        return month.name();
    }

    public int getFirstDateOfWeek() {
        LocalDate localDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());

        return localDate.getDayOfWeek().getValue();
    }

    public int getCurrentYear() {
        return calendar.get(Calendar.YEAR);
    }
}

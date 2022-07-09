package com.example.todogo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TodoGoUtils {

    public static String getDateToday() {
        LocalDate localDate = LocalDate.now();

        return localDate.format(DateTimeFormatter
                .ofLocalizedDate(FormatStyle.FULL));
    }
}

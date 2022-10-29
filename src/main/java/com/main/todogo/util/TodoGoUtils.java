package com.main.todogo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TodoGoUtils {

    public static String getDateToday() {
        return LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    }
}

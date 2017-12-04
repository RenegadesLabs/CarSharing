package com.cardee.custom.calendar.domain.calendar;

import com.cardee.custom.calendar.model.Day;
import com.cardee.custom.calendar.model.Month;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class GenerateCalendarDelegate {

    List<Month> onGenerateFromNextToDate(int range, Day day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day.getCalendarTime());
        calendar.add(Calendar.MONTH, 1);
        return onGenerateMonths(range, calendar);
    }

    List<Month> onGenerateFromCurrent(int range) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return onGenerateMonths(range, calendar);
    }

    private List<Month> onGenerateMonths(int range, Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        List<Month> months = new ArrayList<>(range);
        for (int i = 0; i < range; i++) {
            int monthNumber = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            List<Day> days = new ArrayList<>(daysInMonth);
            for (int j = 1; j <= daysInMonth; j++) {
                Day day = Day.from(calendar.getTime());
                days.add(day);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            months.add(new Month(days, monthNumber, year));
        }
        return months;
    }
}

package com.cardee.custom.calendar.model;


import android.support.annotation.NonNull;

import java.util.List;

public class Month {

    private final List<Day> days;
    private final int monthNumber;
    private final int year;

    public Month(@NonNull List<Day> days, int monthNumber, int year) {
        this.days = days;
        this.monthNumber = monthNumber;
        this.year = year;
    }

    public Day getFirstDay() {
        return days.isEmpty() ? null : days.get(0);
    }

    public List<Day> getDays() {
        return days;
    }

    public int getNumber() {
        return monthNumber;
    }

    public int getYear() {
        return year;
    }

    @Override
    public int hashCode() {
        Day firstDay = getFirstDay();
        return firstDay == null ? 0 : firstDay.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().getName().equals(getClass().getName())) {
            return false;
        }
        Month target = (Month) obj;
        Day firstDay = getFirstDay();
        return firstDay != null && firstDay.equals(target.getFirstDay());
    }
}

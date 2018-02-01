package com.cardee.custom.time_picker.model;


import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Day {

    private static final String[] MONTHES = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "November", "December"};
    private static final String[] DAYZ = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hh", Locale.getDefault());

    private final List<Hour> hours;
    private final Date date;
    private final String dateTitle;
    private final int month;
    private final int year;

    public Day(@NonNull List<Hour> hours, Date date) {
        this.date = date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.hours = hours;
        this.month = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);
        dateTitle = calendar.get(Calendar.DAY_OF_MONTH) + " " +
                MONTHES[month] + ", " + DAYZ[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public List<Hour> getHours() {
        return hours;
    }

    public int getNumber() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getDateTitle() {
        return dateTitle;
    }

    @Override
    public int hashCode() {
        String formatted = dateFormat.format(date);
        return formatted.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().getName().equals(getClass().getName())) {
            return false;
        }
        Day target = (Day) obj;
        return dateFormat.format(date).equals(dateFormat.format(target.date));
    }
}

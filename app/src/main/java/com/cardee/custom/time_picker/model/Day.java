package com.cardee.custom.time_picker.model;


import android.support.annotation.NonNull;

import com.cardee.CardeeApp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Day {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hh", Locale.US);
    private static final SimpleDateFormat titleFormat = new SimpleDateFormat("dd MMMM, EEEE", Locale.US);

    private final List<Hour> hours;
    private final Date date;
    private final String dateTitle;
    private final int month;
    private final int year;

    public Day(@NonNull List<Hour> hours, Date date) {
        this.date = date;
        dateFormat.setTimeZone(CardeeApp.getTimeZone());
        titleFormat.setTimeZone(CardeeApp.getTimeZone());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(CardeeApp.getTimeZone());
        calendar.setTime(date);
        this.hours = hours;
        this.month = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);
        dateTitle = titleFormat.format(date);
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

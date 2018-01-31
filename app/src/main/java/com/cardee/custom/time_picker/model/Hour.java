package com.cardee.custom.time_picker.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.cardee.custom.time_picker.domain.criteria.SelectionState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Hour implements Comparable<Hour> {

    private static final SimpleDateFormat COMPARE_TIME_FORMAT =
            new SimpleDateFormat("yyyyMMdd_HH", Locale.getDefault());

    private final Calendar calendar;
    private final String hourTitle;
    private final int hourOfDay;
    private final boolean enabled;
    private boolean selected;
    private SelectionState state;

    private Hour(@NonNull Date date) {
        this.calendar = Calendar.getInstance();
        calendar.setTime(date);
        String ampm = calendar.get(Calendar.AM_PM) == Calendar.AM ? "am" : "pm";
        hourTitle = String.valueOf(calendar.get(Calendar.HOUR)) + ampm;
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        Date now = new Date();
        int currentCompare = compareTo(now);
        enabled = currentCompare >= 0;
    }

    public static Hour from(@NonNull Date date) {
        return new Hour(date);
    }

    public void setSelectionState(SelectionState state) {
        this.state = state;
        this.selected = state != null;
    }

    public boolean isSelected() {
        return selected;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public String getHourTitle() {
        return hourTitle;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Date getCalendarTime() {
        return calendar.getTime();
    }

    public SelectionState getSelectionState() {
        return state;
    }

    @Override
    public int hashCode() {
        return COMPARE_TIME_FORMAT.format(calendar.getTime()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().getName().equals(getClass().getName())) {
            return false;
        }
        Hour target = (Hour) obj;
        return equalsDate(target.calendar.getTime());
    }

    public boolean equalsDate(Date date) {
        String targetDate = COMPARE_TIME_FORMAT.format(date);
        String currentDate = COMPARE_TIME_FORMAT.format(calendar.getTime());
        return currentDate.equals(targetDate);
    }

    @Override
    public int compareTo(@NonNull Hour hour) {
        return compareTo(hour.calendar);
    }

    private int compareTo(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return compareTo(calendar);
    }

    private int compareTo(Calendar calendar) {
        int targetYear = calendar.get(Calendar.YEAR);
        int currentYear = this.calendar.get(Calendar.YEAR);
        if (currentYear != targetYear) {
            return currentYear < targetYear ? -1 : 1;
        }
        int targetMonth = calendar.get(Calendar.MONTH);
        int currentMonth = this.calendar.get(Calendar.MONTH);
        if (currentMonth != targetMonth) {
            return currentMonth < targetMonth ? -1 : 1;
        }
        int targetDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentDay = this.calendar.get(Calendar.DAY_OF_MONTH);
        if (currentDay != targetDay) {
            return currentDay < targetDay ? -1 : 1;
        }
        int targetHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentHour = this.calendar.get(Calendar.HOUR_OF_DAY);
        if (currentHour != targetHour) {
            return currentHour < targetHour ? -1 : 1;
        }
        return 0;
    }
}

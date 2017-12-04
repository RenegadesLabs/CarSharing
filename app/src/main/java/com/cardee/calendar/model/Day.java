package com.cardee.calendar.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.cardee.calendar.domain.criteria.SelectionState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Day implements Comparable {

    private static final String TAG = Day.class.getSimpleName();
    private static final SimpleDateFormat NEW_DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final SimpleDateFormat COMPARE_DATE_FORMAT =
            new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

    private final Calendar calendar;
    private final String dateTitle;
    private final int dayOfWeek;
    private final boolean current;
    private final boolean enabled;
    private final boolean empty;
    private boolean selected;
    private SelectionState state;

    private Day(@NonNull Date date) {
        this.calendar = Calendar.getInstance();
        calendar.setTime(date);
        dateTitle = String.valueOf(calendar.get(Calendar.DATE));
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Date now = new Date();
        int currentCompare = compareTo(now);
        current = currentCompare == 0;
        enabled = currentCompare >= 0;
        empty = false;
    }

    private Day() {
        calendar = null;
        dateTitle = null;
        dayOfWeek = -1;
        current = false;
        enabled = false;
        empty = true;
    }

    public static Day from(@NonNull Date date) {
        return new Day(date);
    }

    public static Day empty() {
        return new Day();
    }


    /**
     * @param date - string representation of date
     * @return Day object in case of valid date string. Otherwise returns null
     */
    public static Day from(@NonNull String date) {
        try {
            Date dateArg = NEW_DATE_FORMAT.parse(date);
            return new Day(dateArg);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public void setSelectionState(SelectionState state) {
        this.state = state;
        this.selected = state != null;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isCurrent() {
        return current;
    }

    public String getTitle() {
        return dateTitle;
    }

    public int getDayOfWeed() {
        return dayOfWeek;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isEmpty() {
        return empty;
    }

    public Date getCalendarTime() {
        return calendar.getTime();
    }

    public SelectionState getSelectionState() {
        return state;
    }

    @Override
    public int hashCode() {
        return COMPARE_DATE_FORMAT.format(calendar.getTime()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().getName().equals(getClass().getName())) {
            return false;
        }
        Day target = (Day) obj;
        return equalsDate(target.calendar.getTime());
    }

    public boolean equalsDate(Date date) {
        String targetDate = COMPARE_DATE_FORMAT.format(date);
        String currentDate = COMPARE_DATE_FORMAT.format(calendar.getTime());
        return currentDate.equals(targetDate);
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Day day = (Day) o;
        return compareTo(day.calendar);
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
        return 0;
    }
}

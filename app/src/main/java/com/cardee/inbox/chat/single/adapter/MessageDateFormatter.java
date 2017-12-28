package com.cardee.inbox.chat.single.adapter;

import com.cardee.inbox.chat.list.adapter.UtcDateFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MessageDateFormatter implements UtcDateFormatter.ChatMessageFormatter {

    private final SimpleDateFormat mUtcDateFormat;
    private final SimpleDateFormat mMessageFormatter;
    private final SimpleDateFormat mDateFormatter;

    public MessageDateFormatter() {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
        mUtcDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.US);
        mUtcDateFormat.setTimeZone(timeZone);
        mMessageFormatter = new SimpleDateFormat("HH:mm", Locale.US);
        mDateFormatter = new SimpleDateFormat("E, d MMM", Locale.US);
    }

    @Override
    public String formatDate(String utcDate) {
        String date;
        try {
            Date messageTime = mUtcDateFormat.parse(utcDate);
            date = mMessageFormatter.format(messageTime);
        } catch (ParseException e) {
            date = utcDate;
        }
        return date;
    }

    @Override
    public String getDivider() {
        return null;
    }

    @Override
    public String formatDividerDate(String utcDate) {
        String date;
        try {
            Date messageTime = mUtcDateFormat.parse(utcDate);
            date = mDateFormatter.format(messageTime);
        } catch (ParseException e) {
            date = utcDate;
        }
        return date;
    }

    @Override
    public boolean hasSameDate(String firstDate, String secondDate) {
        try {
            Date first = mUtcDateFormat.parse(firstDate);
            Date second = mUtcDateFormat.parse(secondDate);
            return (int) (TimeUnit.MILLISECONDS.toDays(first.getTime()) - TimeUnit.MILLISECONDS.toDays(second.getTime())) == 0;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public boolean sameWithCurrentDate(String inputDate) {
        try {
            Date currentDate = Calendar.getInstance().getTime();
            Date second = mUtcDateFormat.parse(inputDate);
            return (int) (TimeUnit.MILLISECONDS.toDays(currentDate.getTime()) - TimeUnit.MILLISECONDS.toDays(second.getTime())) == 0;
        } catch (ParseException e) {
            return false;
        }
    }
}

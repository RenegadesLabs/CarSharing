package com.cardee.inbox.chat.single.view;

import android.content.Context;

import com.cardee.inbox.chat.list.adapter.UtcDateFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ChatActivityDateFormatter implements UtcDateFormatter {


    private final Calendar mCalendar;
    private final SimpleDateFormat mUtcDateFormat;
    private final SimpleDateFormat mTimeFormat;



    public ChatActivityDateFormatter(Context context) {
        mCalendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
        mUtcDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.US);
        mTimeFormat = new SimpleDateFormat("d\u00a0MMM yyyy,\u00a0ha", Locale.US);
        mUtcDateFormat.setTimeZone(timeZone);
        mCalendar.setTimeZone(timeZone);
    }

    @Override
    public String formatDate(String utcDate) {
        try {
            Date availabilityDate = mUtcDateFormat.parse(utcDate);
            return mTimeFormat.format(availabilityDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String getDivider() {
        return " - ";
    } //'to' changed to '-' for better UX
}

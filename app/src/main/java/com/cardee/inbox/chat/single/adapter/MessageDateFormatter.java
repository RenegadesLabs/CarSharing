package com.cardee.inbox.chat.single.adapter;

import com.cardee.inbox.chat.list.adapter.UtcDateFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MessageDateFormatter implements UtcDateFormatter {

    private final SimpleDateFormat mUtcDateFormat;
    private final SimpleDateFormat mMessageFormatter;

    public MessageDateFormatter() {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
        mUtcDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.US);
        mUtcDateFormat.setTimeZone(timeZone);
        mMessageFormatter = new SimpleDateFormat("HH:mm", Locale.US);
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
}

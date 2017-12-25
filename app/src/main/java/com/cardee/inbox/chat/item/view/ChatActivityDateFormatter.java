package com.cardee.inbox.chat.item.view;

import android.content.Context;

import com.cardee.R;
import com.cardee.inbox.chat.adapter.UtcDateFormatter;

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
    private final String to;

    public ChatActivityDateFormatter(Context context) {
        mCalendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
        mUtcDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.US);
        mTimeFormat = new SimpleDateFormat("d MMM, ha", Locale.US);
        mUtcDateFormat.setTimeZone(timeZone);
        mCalendar.setTimeZone(timeZone);
        to = context.getString(R.string.to);
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
        return " " + to + " ";
    }
}

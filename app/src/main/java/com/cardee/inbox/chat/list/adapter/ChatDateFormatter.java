package com.cardee.inbox.chat.list.adapter;

import android.content.Context;
import android.util.Log;

import com.cardee.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ChatDateFormatter implements UtcDateFormatter {

    private static final String TAG = UtcDateFormatter.class.getSimpleName();

    private final Date mDate;
    private final DateFormat mUtcDateFormat;
    private final DateFormat mTimeFormat;
    private final DateFormat mChatFormat;
    private final String yesterday;
    private final Calendar mCalendar;

    ChatDateFormatter(Context context) {
        mDate = new Date();
        mCalendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
        mUtcDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.US);
        mChatFormat = new SimpleDateFormat("d MMM", Locale.US);
        mTimeFormat = new SimpleDateFormat("hh:mm aaa", Locale.US);
        mUtcDateFormat.setTimeZone(timeZone);
        mCalendar.setTimeZone(timeZone);
        yesterday = context.getString(R.string.yesterday);
    }

    @Override
    public String formatDate(String utcDate) {
        String newDateString;
        try {
            Date chatMessageDate = mUtcDateFormat.parse(utcDate);
            int dateDifference = getDaysDifference(chatMessageDate);
            switch (dateDifference) {
                case 0:
                    newDateString = mTimeFormat.format(chatMessageDate);
                    break;
                case 1:
                    newDateString = yesterday;
                    break;
                default:
                    newDateString = mChatFormat.format(chatMessageDate);
            }
        } catch (ParseException e) {
//            Log.e(TAG, e.getMessage() + " " + utcDate);
            newDateString = utcDate;
        }

        return newDateString;
    }

    @Override
    public String getDivider() {
        return " ";
    }

    private int getDaysDifference(Date chatMessageDate) {
        return (int) (TimeUnit.MILLISECONDS.toDays(mDate.getTime()) - TimeUnit.MILLISECONDS.toDays(chatMessageDate.getTime()));
    }

    public String getGMTTimeString(int hour) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        Date time = mCalendar.getTime();
        String formattedTime = mTimeFormat.format(time);
        System.out.println(formattedTime);
        return formattedTime;
    }
}

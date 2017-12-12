package com.cardee.owner_car_details.view.service;

import android.content.Context;
import android.widget.TextView;

import com.cardee.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AvailabilityStringDelegate {

    private static final String TIME_PATTERN = "HH:mm:ssXXX";
    private static final String TIME_VIEW_PATTERN = "ha";
    private SimpleDateFormat timeFormatter;
    private SimpleDateFormat timeViewFormatter;
    private TimeZone timeZone;
    private Calendar calendar;
    private String[] saveSuffixes;
    private String[] valueSuffixes;
    private String availabilityPickupPrefix;
    private String availabilityPickupSuffix;
    private String availabilityReturnPrefix;
    private String availabilityReturnSuffix;

    public AvailabilityStringDelegate(Context context) {
        saveSuffixes = context.getResources().getStringArray(R.array.btn_save_title_suffixes);
        valueSuffixes = context.getResources().getStringArray(R.array.days_availability_suffixes);
        timeZone = TimeZone.getTimeZone("GMT+08:00");
        calendar = Calendar.getInstance();
        timeFormatter = new SimpleDateFormat(TIME_PATTERN);
        timeViewFormatter = new SimpleDateFormat(TIME_VIEW_PATTERN);
        calendar.setTimeZone(timeZone);
        timeFormatter.setTimeZone(timeZone);
        timeViewFormatter.setTimeZone(timeZone);
        availabilityPickupPrefix = context.getString(R.string.availability_pickup_prefix);
        availabilityPickupSuffix = context.getString(R.string.availability_pickup_suffix);
        availabilityReturnPrefix = context.getString(R.string.availability_return_prefix);
        availabilityReturnSuffix = context.getString(R.string.availability_return_suffix);
    }

    public void onTitleChanged(TextView view, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Invalid count value: " + count);
        }
        int index = count > 1 ? 2 : count;
        String prefix = index == 0 ? "" : String.valueOf(count) + " ";
        String title = prefix + saveSuffixes[index];
        view.setText(title);
    }

    public void onSetValue(TextView view, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Invalid count value: " + count);
        }
        int index = count > 1 ? 2 : count;
        String prefix = index == 0 ? "" : String.valueOf(count) + " ";
        String title = prefix + valueSuffixes[index];
        view.setText(title);
    }

    public void onSetPickupTime(TextView view, String pickupTime) {
        if (pickupTime != null) {
            try {
                Date date = timeFormatter.parse(pickupTime);
                String formattedTime = timeViewFormatter.format(date);
                String timeTitle = availabilityPickupPrefix + " " +
                        formattedTime.toLowerCase() + " " + availabilityPickupSuffix;
                view.setText(timeTitle);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void onSetReturnTime(TextView view, String returnTime) {
        if (returnTime != null) {
            try {
                Date date = timeFormatter.parse(returnTime);
                String formattedTime = timeViewFormatter.format(date);
                String timeTitle = availabilityReturnPrefix + " " +
                        formattedTime.toLowerCase() + " " + availabilityReturnSuffix;
                view.setText(timeTitle);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public String getSimpleTimeFormat(String time) {
        if (time == null) {
            return null;
        }
        try {
            Date parsed = timeFormatter.parse(time);
            return timeViewFormatter.format(parsed).toLowerCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getGMTTimeString(int hour) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        String formattedTime = timeFormatter.format(time);
        System.out.println(formattedTime);
        return formattedTime;
    }
}

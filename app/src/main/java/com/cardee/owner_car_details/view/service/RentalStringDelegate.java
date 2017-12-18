package com.cardee.owner_car_details.view.service;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cardee.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class RentalStringDelegate {

    private static final String TIME_PATTERN = "HH:mm:ssZZZZZ";
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
    private String availabilityHourlyPrefix;

    public RentalStringDelegate(Context context) {
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
        availabilityHourlyPrefix = context.getString(R.string.hourly_timing_dialog_title);
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

    public void onSetHourlyAvailabilityTime(TextView view, String beginTime, String endTime) {
        if (beginTime == null || endTime == null) {
            view.setText(availabilityHourlyPrefix);
            return;
        }
        String formattedBeginTime = getShortTime(beginTime);
        String formattedEndTime = getShortTime(endTime);
        String hourlyTiming = availabilityHourlyPrefix + " " + formattedBeginTime + " - " + formattedEndTime;
        view.setText(hourlyTiming);
    }

    private String getShortTime(String gmtTime) {
        try {
            Date date = timeFormatter.parse(gmtTime);
            return timeViewFormatter.format(date).toLowerCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onSetHourlyShortTitle(TextView view, int daysCount, String beginTime, String endTime) {
        int index = daysCount > 1 ? 2 : daysCount;
        String prefix = index == 0 ? "" : String.valueOf(daysCount) + " ";
        String title = prefix + valueSuffixes[index];
        if (beginTime != null && endTime != null) {
            title = title + "\n";
            title = title + getShortTime(beginTime) + " - " + getShortTime(endTime);
        }
        view.setText(title);
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

    public void onSetDailyRentalRateFirst(TextView tv, Float amount) {
        if (amount != null) {
            String value = "$" + Float.toString(amount) + " per day (weekdays)";
            tv.setVisibility(View.VISIBLE);
            tv.setText(value);
        }
    }

    public void onSetDailyRentalRateSecond(TextView tv, Float amount) {
        if (amount != null) {
            String value = "$" + Float.toString(amount) + " per day (weekends and P.H.)";
            tv.setVisibility(View.VISIBLE);
            tv.setText(value);
        }
    }

    public void onSetHourlyRentalRateFirst(TextView tv, Float amount) {
        if (amount != null) {
            String value = "$" + Float.toString(amount) + " per hour (off-peak)";
            tv.setVisibility(View.VISIBLE);
            tv.setText(value);
        }
    }

    public void onSetHourlyRentalRateSecond(TextView tv, Float amount) {
        if (amount != null) {
            String value = "$" + Float.toString(amount) + " per hour (peak)";
            tv.setVisibility(View.VISIBLE);
            tv.setText(value);
        }
    }

    public void onSetDailyRentalDiscount(TextView tv, Float discount) {
        if (discount != null) {
            String val = "3 days discount " + Float.toString(discount) + "%";
            tv.setVisibility(View.VISIBLE);
            tv.setText(val);
        }
    }

    public void onSetHourlyRentalMinimum(TextView tv, Integer minimum) {
        if (minimum != null) {
            String val = "Minimum " + Integer.toString(minimum) + (minimum > 1 ? " hours" : " hour");
            tv.setVisibility(View.VISIBLE);
            tv.setText(val);
        }
    }

    public void onSetFuelPolicy(TextView tv, String policyName, String payAmountMileage) {
        if (policyName == null || policyName.isEmpty()) {
            return;
        }
        String val = policyName + (payAmountMileage != null && !payAmountMileage.isEmpty() ? " @ "
                + payAmountMileage + " per km" : "");
        tv.setVisibility(View.VISIBLE);
        tv.setText(val);
    }
}

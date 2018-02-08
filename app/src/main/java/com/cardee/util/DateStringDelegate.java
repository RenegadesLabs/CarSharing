package com.cardee.util;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cardee.R;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateStringDelegate {

    private static final String TIME_PATTERN = "HH:mm:ssZZZZZ";
    private static final String TIME_VIEW_PATTERN = "hha";
    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";
    private static final String DATE_STRING_PATTERN = "EEE, d\u00a0MMM";
    private static final String DATE_VIEW_PATTERN_FULL = "d\u00a0MMM yyyy,\u00a0ha";
    private static final String DATE_VIEW_PATTERN_WITHOUT_TIME = "d MMM, yyyy";
    private static final String DATE_SHORT_VIEW_TITLE_PATTERN = "d\u00a0MMM,\u00a0ha";
    private static final String DATE_SHORT_VIEW_PATTERN = "d\u00a0MMM";
    private static final String CREATION_DATE_VIEW_FORMATTER = "d MMM yyyy, h:mma";

    private SimpleDateFormat timeFormatter;
    private SimpleDateFormat timeViewFormatter;
    private SimpleDateFormat timeLocalFormatter;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat dateViewFormatter;
    private SimpleDateFormat dateViewWithoutTimeFormatter;
    private SimpleDateFormat dateShortViewFormatter;
    private SimpleDateFormat dateShortViewTitleFormatter;
    private SimpleDateFormat creationDateViewFormatter;
    private SimpleDateFormat dateViewString;
    private Calendar calendar;
    private String[] saveSuffixes;
    private String[] valueSuffixes;
    private String availabilityPickupPrefix;
    private String availabilityPickupSuffix;
    private String availabilityReturnPrefix;
    private String availabilityReturnSuffix;
    private String availabilityHourlyPrefix;

    public DateStringDelegate(Context context) {
        saveSuffixes = context.getResources().getStringArray(R.array.btn_save_title_suffixes);
        valueSuffixes = context.getResources().getStringArray(R.array.days_availability_suffixes);
        TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
        calendar = Calendar.getInstance();
        timeFormatter = new SimpleDateFormat(TIME_PATTERN, Locale.US);
        timeViewFormatter = new SimpleDateFormat(TIME_VIEW_PATTERN, Locale.US);
        dateFormatter = new SimpleDateFormat(DATE_PATTERN, Locale.US);
        dateViewString = new SimpleDateFormat(DATE_STRING_PATTERN, Locale.US);
        dateViewFormatter = new SimpleDateFormat(DATE_VIEW_PATTERN_FULL, Locale.US);
        dateViewWithoutTimeFormatter = new SimpleDateFormat(DATE_VIEW_PATTERN_WITHOUT_TIME, Locale.US);
        creationDateViewFormatter = new SimpleDateFormat(CREATION_DATE_VIEW_FORMATTER, Locale.US);
        dateShortViewTitleFormatter = new SimpleDateFormat(DATE_SHORT_VIEW_TITLE_PATTERN, Locale.US);
        dateShortViewFormatter = new SimpleDateFormat(DATE_SHORT_VIEW_PATTERN, Locale.US);
        timeLocalFormatter = new SimpleDateFormat(TIME_VIEW_PATTERN, Locale.US);
        calendar.setTimeZone(timeZone);
        dateViewString.setTimeZone(timeZone);
        timeFormatter.setTimeZone(timeZone);
        timeViewFormatter.setTimeZone(timeZone);
        dateFormatter.setTimeZone(timeZone);
//        dateShortViewTitleFormatter.setTimeZone(timeZone);
        dateViewFormatter.setTimeZone(timeZone);
        dateViewWithoutTimeFormatter.setTimeZone(timeZone);
        creationDateViewFormatter.setTimeZone(timeZone);
        dateShortViewFormatter.setTimeZone(timeZone);

        DateFormatSymbols symbols = new DateFormatSymbols(Locale.US);
        symbols.setAmPmStrings(new String[]{"am", "pm"});
        dateViewFormatter.setDateFormatSymbols(symbols);
        creationDateViewFormatter.setDateFormatSymbols(symbols);
        dateShortViewTitleFormatter.setDateFormatSymbols(symbols);

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

    public String getPickupTime(String pickupTime) {
        String time = null;
        if (pickupTime != null) {
            try {
                Date date = timeFormatter.parse(pickupTime);
                String formattedTime = timeViewFormatter.format(date);
                time = availabilityPickupPrefix + " " +
                        formattedTime.toLowerCase() + " " + availabilityPickupSuffix;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return time;
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

    public String getReturnTime(String returnTime) {
        String time = null;
        if (returnTime != null) {
            try {
                Date date = timeFormatter.parse(returnTime);
                String formattedTime = timeViewFormatter.format(date);
                time = availabilityReturnPrefix + " " +
                        formattedTime.toLowerCase() + " " + availabilityReturnSuffix;
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return time;
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

    public String getShortGMTTime(String time) {
        if (time == null) {
            return null;
        }
        try {
            Date date = timeLocalFormatter.parse(time);
            return timeFormatter.format(date).toLowerCase();
        } catch (ParseException e) {

        }
        return null;
    }

    public String getDateString(String gmtTime) {
        if (gmtTime == null) {
            return null;
        }
        try {
            Date date = dateFormatter.parse(gmtTime);
            return dateViewString.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDateWithoutTimeString(String gmtTime) {
        if (gmtTime == null) {
            return null;
        }
        try {
            Date date = dateFormatter.parse(gmtTime);
            return dateViewWithoutTimeFormatter.format(date);
        } catch (ParseException ex) {

        }
        return null;
    }

    private String capitalize(String string) {
        String[] words = string.split(" ");
        for (int i = 0; i < words.length; i++) {
            String first = words[i].substring(0, 1);
            words[i] = words[i].replaceFirst(first, first.toUpperCase());
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

    public String getGMTTimeString(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormatter.format(date);
    }

    public String getTimeString(Date date) {
        return timeFormatter.format(date);
    }

    public String getTimeTitle(Date date) {
        return dateShortViewFormatter.format(date);
    }

    public String getTimeLongTitle(Date date) {
        return dateShortViewTitleFormatter.format(date);
    }

    public String getTimeFromString(String time) {
        try {
            Date date = dateFormatter.parse(time);
            return dateShortViewTitleFormatter.format(date);
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
        return timeFormatter.format(time);
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
        String val = policyName + ((payAmountMileage != null && !payAmountMileage.isEmpty()
                && !policyName.equals("Return with similar level")) ? " @ "
                + payAmountMileage + " per km" : "");
        tv.setVisibility(View.VISIBLE);
        tv.setText(val);
    }

    public String formatShortBookingDate(String rawDate) {
        if (rawDate == null) {
            return null;
        }
        try {
            Date date = dateFormatter.parse(rawDate);
            return dateViewFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String formatCreationDate(String rawDate) {
        if (rawDate == null) {
            return null;
        }
        try {
            Date date = dateFormatter.parse(rawDate);
            return creationDateViewFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

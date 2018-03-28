package com.cardee.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.NumberPicker;

import com.cardee.CardeeApp;
import com.cardee.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeFrameDelegate {

    private static final String TAG = TimeFrameDelegate.class.getSimpleName();

    private static final String DISPLAYED_PATTERN = "(\\d{1,2})([ap]m).*";
    private static final int MAX_HOURS_DIFF = 13;

    private DateRepresentationDelegate dateDelegate;
    private String dailyPickupTime;
    private String dailyReturnTime;
    private final String[] dailyPickupValues;
    private final String[] dailyReturnValues;
    private String[] dailyReturnTimeDisplayedValues;
    private Pattern timingPatten;
    private Calendar calendar;

    public TimeFrameDelegate(@NonNull Context context) {
        dateDelegate = new DateRepresentationDelegate(context);
        Resources res = context.getResources();
        dailyPickupValues = res.getStringArray(R.array.daily_timing_pickup_values);
        dailyReturnValues = res.getStringArray(R.array.daily_timing_return_values);
        dailyReturnTimeDisplayedValues = Arrays.copyOf(dailyReturnValues, dailyReturnValues.length);
        timingPatten = Pattern.compile(DISPLAYED_PATTERN);
        calendar = Calendar.getInstance();
    }

    public TimeFrameDelegate initDaily(String pickupTime, String returnTime) {
        dailyPickupTime = pickupTime;
        dailyReturnTime = returnTime;
        ensureDailyInit();
        return this;
    }

    private void ensureDailyInit() {
        if (dailyPickupTime == null || dailyReturnTime == null) {
            throw new IllegalStateException("Daily state is not initialized: dailyPickupTime = " +
                    dailyPickupTime + ", dailyReturnTime = " + dailyReturnTime);
        }
    }

    public void onInitDailyPickers(NumberPicker pickupTimePicker, NumberPicker returnTimePicker) {
        initPickerWithValues(pickupTimePicker, dailyPickupValues, null);
        initPickerWithValues(returnTimePicker, dailyReturnTimeDisplayedValues, null);
        preventTimeUndervalueSelection(pickupTimePicker, returnTimePicker);
        setPriorSelection();
    }

    private void initPickerWithValues(NumberPicker picker, String[] values, Integer selected) {
        picker.setDisplayedValues(null);
        picker.setMinValue(0);
        picker.setMaxValue(values.length - 1);
        picker.setWrapSelectorWheel(false);
        picker.setDisplayedValues(values);
        if (selected != null) {
            picker.setValue(selected);
        }
    }

    private void preventTimeUndervalueSelection(NumberPicker pickupTimePicker, NumberPicker returnTimePicker) {
        pickupTimePicker.setOnValueChangedListener((numberPicker, old, current) -> {
            int position = returnTimePicker.getValue();
            String value = dailyReturnTimeDisplayedValues[position];
            int diff = dailyReturnValues.length - 1 - current;
            dailyReturnTimeDisplayedValues = Arrays.copyOfRange(
                    dailyReturnValues,
                    current,
                    diff > MAX_HOURS_DIFF ? current + MAX_HOURS_DIFF : dailyReturnValues.length - 1);
            Integer newPosition = null;
            for (int index = 0; index < dailyReturnTimeDisplayedValues.length; index++) {
                if (dailyReturnTimeDisplayedValues[index].equalsIgnoreCase(value)) {
                    newPosition = index;
                    break;
                }
            }
            initPickerWithValues(returnTimePicker, dailyReturnTimeDisplayedValues, newPosition);
        });
    }

    private void setPriorSelection() {
        try {
            ensureDailyInit();
        } catch (IllegalStateException ex) {
            Log.i(TAG, "No prior selection");
        }
    }

    public String onGetPickupTime(NumberPicker picker) {
        int index = picker.getValue();
        String time = dailyPickupValues[index];
        return formatPickedTime(time);
    }

    public String onGetReturnTime(NumberPicker picker) {
        int index = picker.getValue();
        String time = dailyReturnTimeDisplayedValues[index];
        return formatPickedTime(time);
    }

    private String formatPickedTime(String time) {
        Matcher matcher = timingPatten.matcher(time);
        if (matcher.matches()) {
            String hours = matcher.group(1);
            String ampm = matcher.group(2);
            calendar.setTimeZone(CardeeApp.getTimeZone());
            calendar.setTime(
                    dateDelegate.convertTimeToDate(
                            dateDelegate.formatAsIsoTime(hours + ampm)));
            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            return dateDelegate.formatAsIsoTime(hourOfDay);
        }
        throw new IllegalArgumentException("Invalid time format: " + time);
    }

    public void updateReturnRange(NumberPicker pickupTimePicker, NumberPicker returnTimePicker) {
        int selected = returnTimePicker.getValue();
        int pickupV = pickupTimePicker.getValue();
        int diff = dailyReturnValues.length - 1 - pickupV;
        dailyReturnTimeDisplayedValues = Arrays.copyOfRange(
                dailyReturnValues,
                pickupV,
                diff > MAX_HOURS_DIFF ? pickupV + MAX_HOURS_DIFF : dailyReturnValues.length - 1);
        initPickerWithValues(returnTimePicker, dailyReturnTimeDisplayedValues, selected - pickupV);
    }
}

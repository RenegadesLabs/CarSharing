package com.cardee.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.NumberPicker;

import com.cardee.R;

public class TimeFrameDelegate {

    private static final String TAG = TimeFrameDelegate.class.getSimpleName();

    private DateStringDelegate stringDelegate;
    private String dailyPickupTime;
    private String dailyReturnTime;
    private final String[] dailyPickupValues;
    private final String[] dailyReturnValues;

    public TimeFrameDelegate(@NonNull Context context) {
        stringDelegate = new DateStringDelegate(context);
        Resources res = context.getResources();
        dailyPickupValues = res.getStringArray(R.array.daily_timing_pickup_values);
        dailyReturnValues = res.getStringArray(R.array.daily_timing_return_values);
    }

    public TimeFrameDelegate initDaily(String pickupTime, String returnTime) {
        dailyPickupTime = pickupTime;
        dailyReturnTime = returnTime;
        checkDailyInit();
        return this;
    }

    private void checkDailyInit() {
        if (dailyPickupTime == null || dailyReturnTime == null) {
            throw new IllegalStateException("Daily state is not initialized: dailyPickupTime = " +
                    dailyPickupTime + ", dailyReturnTime = " + dailyReturnTime);
        }
    }

    public String getDailyPickupTimeDisplayedValue() {

        return null;
    }

    public String getDailyReturnTimeDisplayedValues() {

        return null;
    }

    public void onInitDailyPickers(NumberPicker pickupTimePicker, NumberPicker returnTimePicker) {
        initPickerWithValues(pickupTimePicker, dailyPickupValues);
        initPickerWithValues(returnTimePicker, dailyReturnValues);
        preventReturnUndervalueSelection(pickupTimePicker, returnTimePicker);
        setPriorSelection();
    }

    private void initPickerWithValues(NumberPicker picker, String[] values) {
        picker.setDisplayedValues(values);
        picker.setMinValue(0);
        picker.setMaxValue(values.length - 1);
    }

    private void preventReturnUndervalueSelection(NumberPicker pickupTimePicker, NumberPicker returnTimePicker) {
        pickupTimePicker.setOnValueChangedListener((numberPicker, i, i1) -> {

        });
    }

    private void setPriorSelection() {
        try {
            checkDailyInit();

        } catch (IllegalStateException ex) {
            Log.i(TAG, "No prior selection");
        }
    }
}

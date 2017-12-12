package com.cardee.custom.modal;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewParent;
import android.widget.NumberPicker;

import com.cardee.R;
import com.cardee.owner_car_details.view.eventbus.HourlyTimingEventBus;
import com.cardee.owner_car_details.view.eventbus.TimingSaveEvent;

import java.lang.reflect.Field;

public class HourlyAvailabilityTimingFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {

    private final static String TIME_BEGIN = "time_begin";
    private final static String TIME_END = "time_end";

    private String[] timeValues;
    private NumberPicker beginTimePicker;
    private NumberPicker endTimePicker;

    public static HourlyAvailabilityTimingFragment newInstance(String timeBegin, String timeEnd) {
        HourlyAvailabilityTimingFragment fragment = new HourlyAvailabilityTimingFragment();
        Bundle args = new Bundle();
        args.putString(TIME_BEGIN, timeBegin);
        args.putString(TIME_END, timeEnd);
        fragment.setArguments(args);
        return fragment;
    }

    private BottomSheetBehavior.BottomSheetCallback callback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View rootView = View.inflate(getContext(), R.layout.modal_dialog_hourly_timing, null);
        dialog.setContentView(rootView);
        init(rootView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                ((View) rootView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(callback);
        }
        ViewParent parent = rootView.getParent();
        if (parent != null) {
            ((View) parent).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void init(View parent) {
        timeValues = getContext().getResources().getStringArray(R.array.availability_time_titles);
        beginTimePicker = parent.findViewById(R.id.time_begin_picker);
        endTimePicker = parent.findViewById(R.id.time_end_picker);
        beginTimePicker.setDisplayedValues(timeValues);
        beginTimePicker.setMinValue(0);
        beginTimePicker.setMaxValue(timeValues.length - 1);
        endTimePicker.setDisplayedValues(timeValues);
        endTimePicker.setMinValue(0);
        endTimePicker.setMaxValue(timeValues.length - 1);
        parent.findViewById(R.id.btn_timing_save).setOnClickListener(this);
        setDividerColor(beginTimePicker, ContextCompat.getColor(getContext(),
                android.R.color.transparent));
        setDividerColor(endTimePicker, ContextCompat.getColor(getContext(),
                android.R.color.transparent));
        Bundle args = getArguments();
        String timePickup = args.getString(TIME_BEGIN);
        String timeReturn = args.getString(TIME_END);
        int timePickupPosition = 0;
        int timeReturnPosition = 0;
        for (int i = 0; i < timeValues.length; i++) {
            String timeValue = timeValues[i];
            if (timeValue.equals(timePickup)) {
                timePickupPosition = i;
            }
            if (timeValue.equals(timeReturn)) {
                timeReturnPosition = i;
            }
        }
        beginTimePicker.setValue(timePickupPosition);
        endTimePicker.setValue(timeReturnPosition);
    }

    private void setDividerColor(NumberPicker picker, int color) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException | Resources.NotFoundException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_timing_save:
                HourlyTimingEventBus.getInstance().post(new TimingSaveEvent(
                        beginTimePicker.getValue() + 1,
                        endTimePicker.getValue() + 1));
                dismiss();
                break;
        }
    }
}

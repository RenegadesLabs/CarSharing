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
import com.cardee.owner_car_details.view.eventbus.DailyTimingEventBus;
import com.cardee.owner_car_details.view.eventbus.TimingSaveEvent;
import com.cardee.util.TimeFrameDelegate;

import java.lang.reflect.Field;

public class DailyAvailabilityTimingFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private static final String TIME_PICKUP = "time_pickup";
    private static final String TIME_RETURN = "time_return";

    private String[] timeValues;
    private NumberPicker pickupTimePicker;
    private NumberPicker returnTimePicker;
    private TimeFrameDelegate pickerDelegate;

    public static DailyAvailabilityTimingFragment newInstance(String timePickup, String timeReturn) {
        DailyAvailabilityTimingFragment fragment = new DailyAvailabilityTimingFragment();
        Bundle args = new Bundle();
        args.putString(TIME_PICKUP, timePickup);
        args.putString(TIME_RETURN, timeReturn);
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
        View rootView = View.inflate(getContext(), R.layout.modal_dialog_daily_timing, null);
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
        pickerDelegate = new TimeFrameDelegate(getContext());
        timeValues = getContext().getResources().getStringArray(R.array.availability_time_titles);
        pickupTimePicker = parent.findViewById(R.id.time_pickup_picker);
        returnTimePicker = parent.findViewById(R.id.time_return_picker);
//        pickupTimePicker.setWrapSelectorWheel(false);
//        returnTimePicker.setWrapSelectorWheel(false);
//        pickupTimePicker.setDisplayedValues(timeValues);
//        pickupTimePicker.setMinValue(0);
//        pickupTimePicker.setMaxValue(timeValues.length - 1);
//        returnTimePicker.setDisplayedValues(timeValues);
//        returnTimePicker.setMinValue(0);
//        returnTimePicker.setMaxValue(timeValues.length - 1);
        pickerDelegate.onInitDailyPickers(pickupTimePicker, returnTimePicker);
        parent.findViewById(R.id.btn_timing_save).setOnClickListener(this);
        setDividerColor(pickupTimePicker, ContextCompat.getColor(getContext(),
                android.R.color.transparent));
        setDividerColor(returnTimePicker, ContextCompat.getColor(getContext(),
                android.R.color.transparent));
        Bundle args = getArguments();
        String timePickup = args.getString(TIME_PICKUP);
        String timeReturn = args.getString(TIME_RETURN);
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
//        pickupTimePicker.setValue(timePickupPosition);
//        returnTimePicker.setValue(timeReturnPosition);
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
                String pickupTime = pickerDelegate.onGetPickupTime(pickupTimePicker);
                String returnTime = pickerDelegate.onGetReturnTime(returnTimePicker);
                DailyTimingEventBus.getInstance().post(new TimingSaveEvent(pickupTime, returnTime));
                dismiss();
                break;
        }
    }
}

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
import android.widget.Toast;

import com.cardee.R;
import com.cardee.owner_car_details.view.eventbus.HourlyTimingEventBus;
import com.cardee.owner_car_details.view.eventbus.TimingSaveEvent;
import com.cardee.util.DateRepresentationDelegate;

import java.lang.reflect.Field;

public class HourlyAvailabilityTimingFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {

    private final static String TIME_BEGIN = "time_begin";
    private final static String TIME_END = "time_end";
    private final static int MINIMAL_TIME_GAP = 4;

    private String[] timeBeginValues;
    private String[] timeEndValues;
    private NumberPicker beginTimePicker;
    private NumberPicker endTimePicker;
    private DateRepresentationDelegate dateDelegate;
    private Toast currentToast;

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
        dateDelegate = new DateRepresentationDelegate(getContext());
        timeBeginValues = getContext().getResources().getStringArray(R.array.availability_time_begin_titles);
        timeEndValues = getContext().getResources().getStringArray(R.array.availability_time_end_titles);
        beginTimePicker = parent.findViewById(R.id.time_begin_picker);
        endTimePicker = parent.findViewById(R.id.time_end_picker);
        beginTimePicker.setWrapSelectorWheel(false);
        endTimePicker.setWrapSelectorWheel(false);
        beginTimePicker.setDisplayedValues(timeBeginValues);
        beginTimePicker.setMinValue(0);
        beginTimePicker.setMaxValue(timeBeginValues.length - 1);
        endTimePicker.setDisplayedValues(timeEndValues);
        endTimePicker.setMinValue(0);
        endTimePicker.setMaxValue(timeEndValues.length - 1);
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

        for (int i = 0; i < timeBeginValues.length; i++) {
            String timeValue = timeBeginValues[i];
            if (timeValue.equals(timePickup)) {
                timePickupPosition = i;
            }
        }

        for (int i = 0; i < timeEndValues.length; i++) {
            String timeValue = timeEndValues[i];
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
                int begin = beginTimePicker.getValue();
                int end = endTimePicker.getValue();
                if ((begin == 0 && end != 0) || (begin != 0 && end == 0)) {
                    showError();
                    return;
                }
                if (begin == 0 && end == 0) {
                    String timeBegin = DateRepresentationDelegate.DATE_BEGIN_TIME;
                    String timeEnd = DateRepresentationDelegate.DATE_END_TIME;
                    HourlyTimingEventBus.getInstance().post(new TimingSaveEvent(timeBegin, timeEnd));
                    dismiss();
                    return;
                }
                if (((end + 3) - (begin - 1)) < MINIMAL_TIME_GAP) {
                    showError();
                    return;
                }
                String timeBegin = dateDelegate.formatAsIsoTime(begin - 1);
                String timeEnd = dateDelegate.formatAsIsoTime(end + 3);
                HourlyTimingEventBus.getInstance().post(new TimingSaveEvent(timeBegin, timeEnd));
                dismiss();
                break;
        }
    }

    private void showError() {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(getActivity(), R.string.error_time_range_not_valid, Toast.LENGTH_SHORT);
        currentToast.show();
    }
}

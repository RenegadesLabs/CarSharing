package com.cardee.custom.modal;


import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewParent;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.cardee.R;

import java.util.ArrayList;
import java.util.List;

public class BookingPickerMenuFragment extends BottomSheetDialogFragment {

    private static BookingPickerMenuFragment.Mode mMode;

    private static String[] mValues;

    private static String mSelectedValue;

    public enum Mode {
        BOOKING_HOURS, BOOKING_DAYS
    }

    public interface DialogOnClickListener {
        void onDoneClicked(String value);
    }

    private PickerMenuFragment.DialogOnClickListener mListener;

    public void setOnDoneClickListener(PickerMenuFragment.DialogOnClickListener listener) {
        mListener = listener;
    }

    public static BookingPickerMenuFragment getInstance(String selected, Mode mode) {
        BookingPickerMenuFragment fragment = new BookingPickerMenuFragment();
        mSelectedValue = selected;
        mMode = mode;
        switch (mode) {
            case BOOKING_HOURS:
                mValues = initBookingHoursArray();
                break;
            case BOOKING_DAYS:
                mValues = initBookingDaysArray();
                break;
        }
        return fragment;
    }


    private BottomSheetBehavior.BottomSheetCallback mCallback = new BottomSheetBehavior.BottomSheetCallback() {
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
        View rootView = View.inflate(getContext(), R.layout.modal_dialog_hours_picker, null);
        dialog.setContentView(rootView);

        TextView title = rootView.findViewById(R.id.menu_title);
        switch (mMode) {
            case BOOKING_HOURS:
                title.setText(R.string.car_rental_info_instant_criteria);
                break;
            case BOOKING_DAYS:
                title.setText(R.string.car_rental_info_instant_criteria);
                break;
        }

        final NumberPicker np = rootView.findViewById(R.id.np_dialogNumberPicker);
        setDividerColor(np, getActivity().getResources().getColor(R.color.colorPrimary));
        np.setDisplayedValues(mValues);
        np.setMaxValue(mValues.length - 1);
        np.setMinValue(0);

        if (mSelectedValue != null && !mSelectedValue.equals("")) {
            for (int i = 0; i < mValues.length; i++) {
                if (mValues[i].equals(mSelectedValue)) {
                    np.setValue(i);
                    break;
                }
            }
        }

        rootView.findViewById(R.id.b_dialogNumberPickerCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        rootView.findViewById(R.id.b_dialogNumberPickerDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener == null) {
                    return;
                }
                dismiss();
                mListener.onDoneClicked(mValues[np.getValue()]);
            }
        });

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) rootView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mCallback);
        }
        ViewParent parent = rootView.getParent();
        if (parent != null) {
            ((View) parent).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void setDividerColor(NumberPicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
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

    private static String[] initBookingHoursArray() {
        int[] hours = new int[25];
        List<String> values = new ArrayList<>();
        for (int i = 0; i < hours.length; i++) {
            hours[i] = i;
            if (i == 1) {
                values.add(String.valueOf(hours[i] + " hour"));
            } else {
                values.add(String.valueOf(hours[i] + " hours"));
            }
        }
        return values.toArray(new String[values.size()]);
    }


    private static String[] initBookingDaysArray() {
        int[] hours = new int[14];
        List<String> values = new ArrayList<>();
        for (int i = 0; i < hours.length; i++) {
            hours[i] = i + 1;
            if (hours[i] == 1) {
                values.add(String.valueOf(hours[i] + " day"));
            } else {
                values.add(String.valueOf(hours[i] + " days"));
            }
        }
        return values.toArray(new String[values.size()]);
    }


}

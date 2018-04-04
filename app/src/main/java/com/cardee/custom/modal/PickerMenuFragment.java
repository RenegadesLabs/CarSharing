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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PickerMenuFragment extends BottomSheetDialogFragment {

    private final static String[] SEATS = {
            "2-seater",
            "3-seater",
            "4-seater",
            "5-seater",
            "6-seater",
            "7-seater",
            "8-seater"};

    private final static String[] ENGINES = {
            "1.0 litre", "1.1 litre", "1.2 litre", "1.3 litre",
            "1.4 litre", "1.5 litre", "1.6 litre", "1.7 litre",
            "1.8 litre", "1.9 litre", "2.0 litre", "2.1 litre",
            "2.2 litre", "2.3 litre", "2.4 litre", "2.5 litre",
            "2.6 litre", "2.7 litre", "2.8 litre", "2.9 litre",
            "3.0 litre", "3.1 litre", "3.2 litre", "3.3 litre",
            "3.4 litre", "3.5 litre", "3.6 litre", "3.7 litre",
            "3.8 litre", "3.9 litre", "4.0 litre", "4.1 litre",
            "4.2 litre", "4.3 litre", "4.4 litre", "4.5 litre",
            "4.6 litre", "4.7 litre", "4.8 litre", "4.9 litre",
            "5.0 litre", "5.1 litre", "5.2 litre", "5.3 litre",
            "5.4 litre", "5.5 litre", "5.6 litre", "5.7 litre",
            "5.8 litre", "5.9 litre", "6.0 litre", "6.1 litre",
            "6.2 litre", "6.3 litre", "6.4 litre", "6.5 litre",
            "6.6 litre", "6.7 litre", "6.8 litre", "6.9 litre",
            "7.0 litre", "7.1 litre", "7.2 litre", "7.3 litre",
            "7.4 litre", "7.5 litre", "7.6 litre", "7.7 litre",
            "7.8 litre", "7.9 litre", "8.0 litre"};

    public final static String[] TRANSMISSION = {"Automatic", "Manual"};

    public final static String[] BODY_TYPES = {
            "Sedan", "Liftback", "SUV", "Hatchback",
            "Wagon", "Coupe", "Convertible",
            "Minivan", "Pickup", "Van", "Limousin"};

    private static String[] mValues;

    private static String mSelectedValue;

    private static Mode mMode;

    public enum Mode {
        YEAR_OF_MANUFACTURE, SEATING_CAPACITY, ENGINE_CAPACITY,
        TRANSMISSION, BODY_TYPE, DRIVING_EXPERIENCE, MINIMUM_RENTAL_DURATION_DAILY,
        MINIMUM_RENTAL_DURATION_HOURLY
    }

    public interface DialogOnClickListener {
        void onDoneClicked(String value);
    }

    private DialogOnClickListener mListener;

    public void setOnDoneClickListener(DialogOnClickListener listener) {
        mListener = listener;
    }

    public static PickerMenuFragment getInstance(String selected, Mode mode) {
        PickerMenuFragment fragment = new PickerMenuFragment();
        mSelectedValue = selected;
        mMode = mode;
        switch (mode) {
            case YEAR_OF_MANUFACTURE:
                mValues = calculateYearArray();
                break;
            case SEATING_CAPACITY:
                mValues = SEATS;
                break;
            case ENGINE_CAPACITY:
                mValues = ENGINES;
                break;
            case TRANSMISSION:
                mValues = TRANSMISSION;
                break;
            case BODY_TYPE:
                mValues = BODY_TYPES;
                break;
            case DRIVING_EXPERIENCE:
                mValues = initExpValuesArray();
                break;
            case MINIMUM_RENTAL_DURATION_DAILY:
                mValues = initMinRentDurationDays();
                break;
            case MINIMUM_RENTAL_DURATION_HOURLY:
                mValues = initMinRentDurationHours();
                break;
        }
        return fragment;
    }

    private static String[] calculateYearArray() {
        String[] years = new String[10];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        for (int i = years.length - 1; i >= 0; i--) {
            years[i] = String.valueOf(year);
            year--;
        }
        return years;
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
        View rootView = View.inflate(getContext(), R.layout.modal_dialog_numberpicker, null);
        dialog.setContentView(rootView);

        TextView title = rootView.findViewById(R.id.menu_title);
        switch (mMode) {
            case YEAR_OF_MANUFACTURE:
                title.setText(R.string.car_add_info_year);
                break;
            case SEATING_CAPACITY:
                title.setText(R.string.car_add_info_seat_capacity);
                break;
            case ENGINE_CAPACITY:
                title.setText(R.string.car_add_info_eng_capacity);
                break;
            case TRANSMISSION:
                title.setText(R.string.car_add_info_transmission);
                break;
            case BODY_TYPE:
                title.setText(R.string.car_add_info_body);
                break;
            case DRIVING_EXPERIENCE:
                title.setText(R.string.car_rental_terms_requirements_experience);
                break;
            case MINIMUM_RENTAL_DURATION_DAILY:
                title.setText(R.string.car_rental_info_minimum_booking_daily);
                break;
            case MINIMUM_RENTAL_DURATION_HOURLY:
                title.setText(R.string.car_rental_info_minimum_booking_hourly);
                break;
        }

        final NumberPicker np = rootView.findViewById(R.id.np_dialogNumberPicker);
        setDividerColor(np, getActivity().getResources().getColor(android.R.color.transparent));
        np.setDisplayedValues(mValues);
        np.setMaxValue(mValues.length - 1);
        np.setMinValue(0);

        if (mSelectedValue != null && !mSelectedValue.equals("")) {
            for (int i = 0; i < mValues.length; i++) {
                if (mMode.equals(Mode.ENGINE_CAPACITY)) {
                    if (mValues[i].equals(mSelectedValue + " litre")) {
                        np.setValue(i);
                        break;
                    }
                } else {
                    if (mValues[i].equals(mSelectedValue)) {
                        np.setValue(i);
                        break;
                    }
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

    private static String[] initExpValuesArray() {
        int[] years = new int[99];
        List<String> values = new ArrayList<>();
        for (int i = 1; i < years.length; ++i) {
            years[i] = i;
            if (years[i] != 0) {
                values.add(String.valueOf(years[i]));
            }
        }
        return values.toArray(new String[values.size()]);
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

    private static String[] initMinRentDurationDays() {
        int[] days = new int[7];
        List<String> values = new ArrayList<>();
        for (int i = 0; i < days.length; i++) {
            days[i] = i + 1;
            if (i == 0) {
                values.add(String.valueOf(days[i] + " day"));
            } else {
                values.add(String.valueOf(days[i] + " days"));
            }
        }
        return values.toArray(new String[values.size()]);
    }

    private static String[] initMinRentDurationHours() {
        int[] hours = new int[7];
        List<String> values = new ArrayList<>();
        for (int i = 0; i < hours.length; i++) {
            hours[i] = i + 1;
            if (i == 0) {
                values.add(String.valueOf(hours[i] + " hour"));
            } else {
                values.add(String.valueOf(hours[i] + " hours"));
            }
        }
        return values.toArray(new String[values.size()]);
    }
}

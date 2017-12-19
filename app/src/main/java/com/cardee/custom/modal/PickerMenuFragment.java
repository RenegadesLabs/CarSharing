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

public class PickerMenuFragment extends BottomSheetDialogFragment {

    private final static String[] YEARS = {"2007", "2008", "2009", "2010", "2011", "2012",
            "2013", "2014", "2015", "2016", "2017"};

    private final static String[] SEATS = {"2", "3", "4", "5", "6", "7", "8"};

    private final static String[] ENGINES = {
            "1.0", "1.1", "1.2", "1.3", "1.4", "1.5", "1.6",
            "1.7", "1.8", "1.9", "2.0", "2.1", "2.2", "2.3",
            "2.4", "2.5", "2.6", "2.7", "2.8", "2.9", "3.0",
            "3.1", "3.2", "3.3", "3.4", "3.5", "3.6", "3.7",
            "3.8", "3.9", "4.0", "4.1", "4.2", "4.3", "4.4",
            "4.5", "4.6", "4.7", "4.8", "4.9", "5.0", "5.1",
            "5.2", "5.3", "5.4", "5.5", "5.6", "5.7", "5.8",
            "5.9", "6.0", "6.1", "6.2", "6.3", "6.4", "6.5",
            "6.6", "6.7", "6.8", "6.9", "7.0", "7.1", "7.2",
            "7.3", "7.4", "7.5", "7.6", "7.7", "7.8", "7.9", "8.0"};

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
        TRANSMISSION, BODY_TYPE, DRIVING_EXPERIENCE
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
                mValues = YEARS;
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
}

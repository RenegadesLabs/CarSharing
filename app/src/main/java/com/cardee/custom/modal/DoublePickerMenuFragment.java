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

import com.cardee.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DoublePickerMenuFragment extends BottomSheetDialogFragment {

    private static String[] mValues;

    private static String mSelectedValue1;

    private static String mSelectedValue2;

    public interface DialogOnClickListener {
        void onDoneClicked(String value1, String value2);

        void onFail();
    }

    public enum Mode {
        YEARS_RANGE
    }

    private DialogOnClickListener mListener;

    public void setOnDoneClickListener(DialogOnClickListener listener) {
        mListener = listener;
    }

    public static DoublePickerMenuFragment getInstance(String selected1, String selected2, Mode mode) {
        DoublePickerMenuFragment fragment = new DoublePickerMenuFragment();
        mSelectedValue1 = selected1;
        mSelectedValue2 = selected2;

        switch (mode) {
            case YEARS_RANGE:
                mValues = initYearsValues();
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
        View rootView = View.inflate(getContext(), R.layout.modal_dialog_double_numberpicker, null);
        dialog.setContentView(rootView);

        final NumberPicker np1 = rootView.findViewById(R.id.np_dialogNumberPicker1);
        setDividerColor(np1, getActivity().getResources().getColor(android.R.color.transparent));
        np1.setDisplayedValues(mValues);
        np1.setMaxValue(mValues.length - 1);
        np1.setMinValue(0);
        np1.setValue(0);

        if (mSelectedValue1 != null && !mSelectedValue1.equals("")) {
            for (int i = 0; i < mValues.length; i++) {
                if (mValues[i].equals(mSelectedValue1)) {
                    np1.setValue(i);
                    break;
                }
            }
        }

        final NumberPicker np2 = rootView.findViewById(R.id.np_dialogNumberPicker2);
        setDividerColor(np2, getActivity().getResources().getColor(android.R.color.transparent));
        np2.setDisplayedValues(mValues);
        np2.setMaxValue(mValues.length - 1);
        np2.setMinValue(0);
        np2.setValue(mValues.length - 1);

        if (mSelectedValue2 != null && !mSelectedValue2.equals("")) {
            for (int i = 0; i < mValues.length; i++) {
                if (mValues[i].equals(mSelectedValue2)) {
                    np2.setValue(i);
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

                if (Integer.parseInt(mValues[np1.getValue()])
                        >= Integer.parseInt(mValues[np2.getValue()])) {
                    mListener.onFail();
                    return;
                }

                dismiss();
                mListener.onDoneClicked(mValues[np1.getValue()], mValues[np2.getValue()]);
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

    private static String[] initYearsValues() {
        int[] years = new int[99];
        List<String> values = new ArrayList<>();
        for (int i = 18; i < years.length; ++i) {
            years[i] = i;
            if (years[i] != 0) {
                values.add(String.valueOf(years[i]));
            }
        }
        return values.toArray(new String[values.size()]);
    }
}

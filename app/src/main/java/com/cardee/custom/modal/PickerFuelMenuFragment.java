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

public class PickerFuelMenuFragment extends BottomSheetDialogFragment {

    private final static String[] COSTS = {"$0.10", "$0.12", "$0.14", "$0.16", "$0.18", "$0.20",
            "$0.25", "$0.30", "$0.35", "$0.40", "$0.50"};

    private final static String[] CONSUMPTIONS = {
            "> 20 km per litre", "18 to 20 km per litre", "15 to 17 km per litre",
            "13 to 15 km per litre", "11 to 13 km per litre", "10 to 12 km per litre",
            "8 to 10 km per litre", "7 to 8 km per litre", "6 to 7 km per litre",
            "5 to 6 km per litre", "4 to 5 km per litre"};


    private static String mSelectedValue;


    public interface DialogOnClickListener {
        void onDoneClicked(String value1, String value2);
    }

    private DialogOnClickListener mListener;

    public void setOnDoneClickListener(DialogOnClickListener listener) {
        mListener = listener;
    }

    public static PickerFuelMenuFragment getInstance(String selected) {
        PickerFuelMenuFragment fragment = new PickerFuelMenuFragment();
        mSelectedValue = selected;
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
        View rootView = View.inflate(getContext(), R.layout.modal_dialog_fuel_numberpicker, null);
        dialog.setContentView(rootView);

        final NumberPicker np1 = rootView.findViewById(R.id.np_dialogNumberPicker1);
        np1.setDisplayedValues(COSTS);
        np1.setMaxValue(COSTS.length - 1);
        np1.setMinValue(0);

        final NumberPicker np2 = rootView.findViewById(R.id.np_dialogNumberPicker2);
        np2.setDisplayedValues(CONSUMPTIONS);
        np2.setMaxValue(CONSUMPTIONS.length - 1);
        np2.setMinValue(0);

        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if (i1 == np2.getValue())
                    return;

                np2.setValue(i1);
            }
        });

        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if (i1 == np1.getValue())
                    return;

                np1.setValue(i1);
            }
        });

        setDividerColor(np1, getActivity().getResources().getColor(android.R.color.transparent));
        setDividerColor(np2, getActivity().getResources().getColor(android.R.color.transparent));

        if (mSelectedValue != null && !mSelectedValue.equals("")) {
            for (int i = 0; i < COSTS.length; i++) {
                if (COSTS[i].equals(mSelectedValue)) {
                    np1.setValue(i);
                    np2.setValue(i);
                    break;
                }
            }
        }

        rootView.findViewById(R.id.b_dialogNumberPickerSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener == null) {
                    return;
                }
                dismiss();
                mListener.onDoneClicked(COSTS[np1.getValue()], CONSUMPTIONS[np2.getValue()]);
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

    public String getCurrentConsumption(String cost) {
        for (int i = 0; i < COSTS.length; i++) {
            if (cost.equals(COSTS[i])) {
                return CONSUMPTIONS[i];
            }
        }
        return "";
    }
}

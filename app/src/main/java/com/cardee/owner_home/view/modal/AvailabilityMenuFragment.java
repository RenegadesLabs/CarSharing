package com.cardee.owner_home.view.modal;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Toast;

import com.cardee.R;

public class AvailabilityMenuFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {

    public enum Mode {
        DAILY, HOURLY
    }

    private Mode mMode;
    private Toast mCurrentToast;

    public static AvailabilityMenuFragment getInstance(Mode mode) {
        AvailabilityMenuFragment fragment = new AvailabilityMenuFragment();
        fragment.mMode = mode;
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
        View rootView = View.inflate(getContext(), R.layout.modal_dialog_availability, null);
        dialog.setContentView(rootView);
        setClickListeners(rootView);
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

    private void setClickListeners(View root) {
        root.findViewById(R.id.availability_one_time).setOnClickListener(this);
        root.findViewById(R.id.availability_calendar).setOnClickListener(this);
        root.findViewById(R.id.availability_days_of_week).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.availability_one_time:
                showMessage("Coming soon");
                break;
            case R.id.availability_calendar:
                showMessage("Coming soon");
                break;
            case R.id.availability_days_of_week:
                showMessage("Coming soon");
                break;
        }
    }

    private void showMessage(String message) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }
}

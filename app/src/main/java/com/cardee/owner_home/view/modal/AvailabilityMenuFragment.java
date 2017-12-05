package com.cardee.owner_home.view.modal;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.owner_car_details.AvailabilityContract;
import com.cardee.owner_car_details.view.AvailabilityCalendarActivity;

public class AvailabilityMenuFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {

    private Toast mCurrentToast;

    public static AvailabilityMenuFragment getInstance(int id, AvailabilityContract.Mode mode) {
        AvailabilityMenuFragment fragment = new AvailabilityMenuFragment();
        Bundle args = new Bundle();
        args.putInt(AvailabilityContract.CAR_ID, id);
        args.putSerializable(AvailabilityContract.CALENDAR_MODE, mode);
        fragment.setArguments(args);
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
                Intent intent = new Intent(getActivity(), AvailabilityCalendarActivity.class);
                intent.putExtras(getArguments());
                getActivity().startActivity(intent);
                break;
            case R.id.availability_days_of_week:
                showMessage("Coming soon");
                break;
        }
        dismiss();
    }

    private void showMessage(String message) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }
}

package com.cardee.custom.modal;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.view.View;
import android.view.ViewParent;

import com.cardee.R;
import com.cardee.domain.bookings.BookingState;

public class FilterBookingDialog extends BottomSheetDialogFragment {

    private static final String BOOKING_STATE = "booking_state";

    AppCompatCheckedTextView mSelected;
    private BookingState state;

    public interface MenuListeners {
        void onDoneClicked(AppCompatCheckedTextView selected, CharSequence value, Integer id);

        void onConvertibleClicked();
    }

    public static FilterBookingDialog getInstance(BookingState state) {
        FilterBookingDialog fragment = new FilterBookingDialog();
        Bundle args = new Bundle();
        args.putSerializable(BOOKING_STATE, state);
        fragment.setArguments(args);
        return fragment;
    }

    private MenuListeners mListener;

    public void setListeners(MenuListeners listener) {
        mListener = listener;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            state = (BookingState) args.getSerializable(BOOKING_STATE);
        }
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View rootView = View.inflate(getContext(), R.layout.modal_dialog_booking_filter, null);
        dialog.setContentView(rootView);

//        AppCompatCheckedTextView sedan = rootView.findViewById(R.id.body_sedan);
//        AppCompatCheckedTextView hatchback = rootView.findViewById(R.id.body_hatchback);
//        AppCompatCheckedTextView suv = rootView.findViewById(R.id.body_suv);
//        AppCompatCheckedTextView sports = rootView.findViewById(R.id.body_sports);
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
}

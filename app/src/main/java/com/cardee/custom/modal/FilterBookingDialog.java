package com.cardee.custom.modal;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.usecase.ObtainBookings;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FilterBookingDialog extends BottomSheetDialogFragment {

    private static final String BOOKING_STATE = "_booking_filter";

    private BookingState currentFilter;
    private FilterSelectListener listener;

    @BindView(R.id.booking_filter_all)
    TextView filterAll;
    @BindView(R.id.booking_filter_new)
    TextView filterNew;
    @BindView(R.id.booking_filter_confirmed)
    TextView filterConfirmed;
    @BindView(R.id.booking_filter_collecting)
    TextView filterCollecting;
    @BindView(R.id.booking_filter_collected)
    TextView filterCollected;
    @BindView(R.id.booking_filter_completed)
    TextView filterCompleted;
    @BindView(R.id.booking_filter_canceled)
    TextView filterCanceled;

    @BindView(R.id.booking_filter_all_selected)
    ImageView filterAllSelected;
    @BindView(R.id.booking_filter_new_selected)
    ImageView filterNewSelected;
    @BindView(R.id.booking_filter_confirmed_selected)
    ImageView filterConfirmedSelected;
    @BindView(R.id.booking_filter_collecting_selected)
    ImageView filterCollectingSelected;
    @BindView(R.id.booking_filter_collected_selected)
    ImageView filterCollectedSelected;
    @BindView(R.id.booking_filter_completed_selected)
    ImageView filterCompletedSelected;
    @BindView(R.id.booking_filter_canceled_selected)
    ImageView filterCanceledSelected;

    private Unbinder unbinder;

    public static FilterBookingDialog getInstance(BookingState state) {
        FilterBookingDialog fragment = new FilterBookingDialog();
        Bundle args = new Bundle();
        args.putSerializable(BOOKING_STATE, state);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            currentFilter = (BookingState) args.getSerializable(BOOKING_STATE);
        }
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View rootView = View.inflate(getContext(), R.layout.modal_dialog_booking_filter, null);
        unbinder = ButterKnife.bind(this, rootView);
        changeSelection(currentFilter);
        dialog.setContentView(rootView);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        listener = null;
    }

    @OnClick({R.id.booking_filter_all,
            R.id.booking_filter_new,
            R.id.booking_filter_confirmed,
            R.id.booking_filter_collecting,
            R.id.booking_filter_collected,
            R.id.booking_filter_completed,
            R.id.booking_filter_canceled})
    public void onSortSelected(View sortView) {
        BookingState filter = null;
        switch (sortView.getId()) {
            case R.id.booking_filter_all:
                break;
            case R.id.booking_filter_new:
                filter = BookingState.NEW;
                break;
            case R.id.booking_filter_confirmed:
                filter = BookingState.CONFIRMED;
                break;
            case R.id.booking_filter_collecting:
                filter = BookingState.COLLECTING;
                break;
            case R.id.booking_filter_collected:
                filter = BookingState.COLLECTED;
                break;
            case R.id.booking_filter_completed:
                filter = BookingState.COMPLETED;
                break;
            case R.id.booking_filter_canceled:
                filter = BookingState.CANCELED;
                break;
        }
        if ((currentFilter != null && !currentFilter.equals(filter)) ||
                (filter != null && !filter.equals(currentFilter))) {
//            changeSelection(filter);
            if (listener != null) {
                listener.onFilterSelected(filter);
            }
            dismiss();
        }
    }

    private void changeSelection(BookingState filter) {
        currentFilter = filter;
        filterAll.setTypeface(filter == null ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        filterNew.setTypeface(BookingState.NEW.equals(filter) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        filterConfirmed.setTypeface(BookingState.CONFIRMED.equals(filter) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        filterCollecting.setTypeface(BookingState.COLLECTING.equals(filter) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        filterCollected.setTypeface(BookingState.COLLECTED.equals(filter) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        filterCompleted.setTypeface(BookingState.COMPLETED.equals(filter) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        filterCanceled.setTypeface(BookingState.CANCELED.equals(filter) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        filterAllSelected.setVisibility(filter == null ? View.VISIBLE : View.GONE);
        filterNewSelected.setVisibility(BookingState.NEW.equals(filter) ? View.VISIBLE : View.GONE);
        filterConfirmedSelected.setVisibility(BookingState.CONFIRMED.equals(filter) ? View.VISIBLE : View.GONE);
        filterCollectingSelected.setVisibility(BookingState.COLLECTING.equals(filter) ? View.VISIBLE : View.GONE);
        filterCollectedSelected.setVisibility(BookingState.COLLECTED.equals(filter) ? View.VISIBLE : View.GONE);
        filterCompletedSelected.setVisibility(BookingState.COMPLETED.equals(filter) ? View.VISIBLE : View.GONE);
        filterCanceledSelected.setVisibility(BookingState.CANCELED.equals(filter) ? View.VISIBLE : View.GONE);
    }

    public void setFilterSelectListener(FilterSelectListener listener) {
        this.listener = listener;
    }

    public interface FilterSelectListener {
        void onFilterSelected(BookingState filter);
    }
}

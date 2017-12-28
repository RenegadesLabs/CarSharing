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
import com.cardee.domain.bookings.usecase.ObtainBookings;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SortBookingDialog extends BottomSheetDialogFragment {

    private static final String SORT = "_booking_sort";

    private ObtainBookings.Sort currentSort;
    private SortSelectListener listener;

    @BindView(R.id.booking_sort_date)
    TextView sortDate;
    @BindView(R.id.booking_sort_pickup_date)
    TextView sortPickupDate;
    @BindView(R.id.booking_sort_return_date)
    TextView sortReturnDate;
    @BindView(R.id.booking_sort_amount)
    TextView sortAmount;

    @BindView(R.id.booking_sort_date_selected)
    ImageView sortDateSelected;
    @BindView(R.id.booking_sort_pickup_date_selected)
    ImageView sortPickupDateSelected;
    @BindView(R.id.booking_sort_return_date_selected)
    ImageView sortReturnDateSelected;
    @BindView(R.id.booking_sort_amount_selected)
    ImageView sortAmountSelected;

    private Unbinder unbinder;

    public static SortBookingDialog getInstance(ObtainBookings.Sort sort) {
        SortBookingDialog fragment = new SortBookingDialog();
        Bundle args = new Bundle();
        args.putSerializable(SORT, sort);
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
            currentSort = (ObtainBookings.Sort) args.getSerializable(SORT);
        }
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View rootView = View.inflate(getContext(), R.layout.modal_dialog_booking_sort, null);
        unbinder = ButterKnife.bind(this, rootView);
        dialog.setContentView(rootView);
        changeSelection(currentSort);
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
    }

    @OnClick({R.id.booking_sort_date,
            R.id.booking_sort_pickup_date,
            R.id.booking_sort_return_date,
            R.id.booking_sort_amount})
    public void onSortSelected(View sortView) {
        ObtainBookings.Sort sort = null;
        switch (sortView.getId()) {
            case R.id.booking_sort_date:
                break;
            case R.id.booking_sort_pickup_date:
                sort = ObtainBookings.Sort.PICKUP;
                break;
            case R.id.booking_sort_return_date:
                sort = ObtainBookings.Sort.RETURN;
                break;
            case R.id.booking_sort_amount:
                sort = ObtainBookings.Sort.AMOUNT;
                break;
        }
        if (currentSort != null && !currentSort.equals(sort) ||
                (sort != null && !sort.equals(currentSort))) {
//            changeSelection(sort);
            if (listener != null) {
                listener.onSortSelected(sort);
            }
            dismiss();
        }
    }

    private void changeSelection(ObtainBookings.Sort sort) {
        currentSort = sort;
        sortDate.setTypeface(sort == null ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        sortPickupDate.setTypeface(ObtainBookings.Sort.PICKUP.equals(sort) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        sortReturnDate.setTypeface(ObtainBookings.Sort.RETURN.equals(sort) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        sortAmount.setTypeface(ObtainBookings.Sort.AMOUNT.equals(sort) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        sortDateSelected.setVisibility(sort == null ? View.VISIBLE : View.GONE);
        sortPickupDateSelected.setVisibility(ObtainBookings.Sort.PICKUP.equals(sort) ? View.VISIBLE : View.GONE);
        sortReturnDateSelected.setVisibility(ObtainBookings.Sort.RETURN.equals(sort) ? View.VISIBLE : View.GONE);
        sortAmountSelected.setVisibility(ObtainBookings.Sort.AMOUNT.equals(sort) ? View.VISIBLE : View.GONE);
    }

    public void setSortSelectListener(SortSelectListener listener) {
        this.listener = listener;
    }

    public interface SortSelectListener {
        void onSortSelected(ObtainBookings.Sort sort);
    }
}

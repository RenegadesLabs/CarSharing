package com.cardee.owner_bookings.strategy;


import android.support.annotation.NonNull;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.cardee.R;
import com.cardee.domain.bookings.BookingState;
import com.cardee.owner_bookings.view.BookingView;

public class NewBookingStrategy extends PresentationStrategy implements View.OnClickListener {

    private static final String TAG = NewBookingStrategy.class.getSimpleName();

    private final BookingView bookingView;

    public NewBookingStrategy(@NonNull View view, @NonNull ActionListener listener, boolean isRenter) {
        super(view, listener);
        bookingView = (BookingView) view;
        int statusColor = ContextCompat.getColor(view.getContext(), R.color.booking_state_new);

        bookingView.getBookingStatus().setBackgroundColor(statusColor);
        bookingView.getBookingStatus().setText(isRenter ? R.string.booking_state_new_renter : R.string.booking_state_new);
        bookingView.getRenterNameTitle().setVisibility(View.VISIBLE);
        bookingView.getRenterNameTitle().setText(isRenter ? R.string.booking_owner_title : R.string.booking_request_title);
        bookingView.getRenterName().setVisibility(View.VISIBLE);
        bookingView.getRenterPhoto().setVisibility(View.VISIBLE);
        bookingView.getBookingPayment().setVisibility(View.GONE);
        bookingView.getRentalPeriodTitle().setVisibility(View.VISIBLE);
        bookingView.getRentalPeriod().setVisibility(View.VISIBLE);
        bookingView.getDeliverToTitle().setVisibility(View.VISIBLE);
        bookingView.getDeliverToTitle().setText(isRenter ? R.string.booking_pickup_at_title : R.string.booking_deliver_to_title);
        bookingView.getDeliverTo().setVisibility(View.VISIBLE);
        bookingView.getHandoverOnTitle().setVisibility(View.GONE);
        bookingView.getHandoverOn().setVisibility(View.GONE);
        bookingView.getReturnByTitle().setVisibility(View.GONE);
        bookingView.getReturnBy().setVisibility(View.GONE);
        bookingView.getHandoverAtTitle().setVisibility(View.GONE);
        bookingView.getHandoverAt().setVisibility(View.GONE);
        bookingView.getTotalCostTitle().setVisibility(View.VISIBLE);
        bookingView.getTotalCost().setVisibility(View.VISIBLE);
        bookingView.getRenterMessage().setVisibility(View.VISIBLE);
        bookingView.getRenterCallTitle().setVisibility(View.GONE);
        bookingView.getRenterCall().setVisibility(View.GONE);
        bookingView.getRenterChatTitle().setVisibility(View.GONE);
        bookingView.getRenterChat().setVisibility(View.GONE);
        bookingView.getCancelMessage().setVisibility(isRenter ? View.VISIBLE : View.GONE);
        bookingView.getAcceptMessage().setVisibility(isRenter ? View.GONE : View.VISIBLE);
        bookingView.getBtnAccept().setVisibility(isRenter ? View.GONE : View.VISIBLE);
        bookingView.getBtnCancel().setVisibility(View.VISIBLE);
        bookingView.getRenterPhotoCompleted().setVisibility(View.GONE);
        bookingView.getRatingBlock().setVisibility(View.GONE);
        bookingView.getRatingTitle().setVisibility(View.GONE);
        bookingView.getRatingBar().setVisibility(View.GONE);
        bookingView.getRatingEdit().setVisibility(View.GONE);

        if (!isRenter) {
            bookingView.getBtnAccept().setText(R.string.booking_title_accept);
            bookingView.getBtnAccept().setOnClickListener(this);
            bookingView.getAcceptMessage().setText(R.string.booking_message_accept);
        } else {
            ConstraintSet set = new ConstraintSet();
            set.clone(bookingView.findViewById(R.id.booking_container));
            set.connect(R.id.renter_message, ConstraintSet.BOTTOM, R.id.booking_cancel_message, ConstraintSet.TOP);
            set.applyTo(bookingView.findViewById(R.id.booking_container));
            bookingView.getCancelMessage().setText(R.string.booking_message_accept_renter);
        }
        bookingView.getBtnCancel().setText(isRenter ? R.string.booking_title_cancel_renter : R.string.booking_title_cancel);

        bookingView.getRenterPhoto().setOnClickListener(this);
        bookingView.getBtnCancel().setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (isFinished()) {
            Log.i(TAG, "Strategy is finished");
            return;
        }
        switch (view.getId()) {
            case R.id.renter_photo:
                listener.onShowProfile();
                break;
            case R.id.btn_accept:
                listener.onAccept();
                break;
            case R.id.btn_cancel:
                listener.onDecline();
                break;
        }
    }

    @Override
    public BookingState getType() {
        return BookingState.NEW;
    }
}

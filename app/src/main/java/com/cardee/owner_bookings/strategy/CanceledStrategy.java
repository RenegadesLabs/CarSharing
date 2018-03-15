package com.cardee.owner_bookings.strategy;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.cardee.R;
import com.cardee.domain.bookings.BookingState;
import com.cardee.owner_bookings.view.BookingView;


public class CanceledStrategy extends PresentationStrategy implements View.OnClickListener {

    private static final String TAG = CanceledStrategy.class.getSimpleName();

    private final BookingView bookingView;

    public CanceledStrategy(View view, ActionListener listener) {
        super(view, listener);
        bookingView = (BookingView) view;
        int statusColor = ContextCompat.getColor(view.getContext(), R.color.booking_state_canceled);

        bookingView.getBookingStatus().setBackgroundColor(statusColor);
        bookingView.getBookingStatus().setText(R.string.booking_state_canceled);
        bookingView.getRenterNameTitle().setVisibility(View.VISIBLE);
        bookingView.getRenterName().setVisibility(View.VISIBLE);
        bookingView.getRenterPhoto().setVisibility(View.VISIBLE);
        bookingView.getBookingPayment().setVisibility(View.GONE);
        bookingView.getRentalPeriodTitle().setVisibility(View.GONE);
        bookingView.getRentalPeriod().setVisibility(View.GONE);
        bookingView.getDeliverToTitle().setVisibility(View.GONE);
        bookingView.getDeliverTo().setVisibility(View.GONE);
        bookingView.getHandoverOnTitle().setVisibility(View.GONE);
        bookingView.getHandoverOn().setVisibility(View.GONE);
        bookingView.getReturnByTitle().setVisibility(View.GONE);
        bookingView.getReturnBy().setVisibility(View.GONE);
        bookingView.getHandoverAtTitle().setVisibility(View.GONE);
        bookingView.getHandoverAt().setVisibility(View.GONE);
        bookingView.getTotalCostTitle().setVisibility(View.VISIBLE);
        bookingView.getTotalCost().setVisibility(View.VISIBLE);
        bookingView.getRenterMessage().setVisibility(View.GONE);
        bookingView.getRenterCallTitle().setVisibility(View.GONE);
        bookingView.getRenterCall().setVisibility(View.GONE);
        bookingView.getRenterChatTitle().setVisibility(View.GONE);
        bookingView.getRenterChat().setVisibility(View.GONE);
        bookingView.getCancelMessage().setVisibility(View.GONE);
        bookingView.getAcceptMessage().setVisibility(View.GONE);
        bookingView.getBtnCancel().setVisibility(View.GONE);
        bookingView.getBtnAccept().setVisibility(View.GONE);
        bookingView.getRenterPhotoCompleted().setVisibility(View.GONE);
        bookingView.getRatingBlock().setVisibility(View.GONE);
        bookingView.getRatingTitle().setVisibility(View.GONE);
        bookingView.getRatingBar().setVisibility(View.GONE);
        bookingView.getRatingEdit().setVisibility(View.GONE);
        bookingView.getRenterPhoto().setOnClickListener(this);
    }

    @Override
    public BookingState getType() {
        return BookingState.CANCELED;
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
        }
    }
}

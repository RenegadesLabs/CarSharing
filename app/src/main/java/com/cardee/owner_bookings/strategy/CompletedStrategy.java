package com.cardee.owner_bookings.strategy;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.cardee.R;
import com.cardee.domain.bookings.BookingState;
import com.cardee.owner_bookings.view.BookingView;

public class CompletedStrategy extends PresentationStrategy {

    private final BookingView bookingView;

    public CompletedStrategy(@NonNull View view, @NonNull ActionListener listener, boolean isRenter) {
        super(view, listener);
        bookingView = (BookingView) view;
        int statusColor = ContextCompat.getColor(view.getContext(), R.color.booking_state_completed);

        bookingView.getBookingStatus().setBackgroundColor(statusColor);
        bookingView.getBookingStatus().setText(R.string.booking_state_completed);
        bookingView.getBookingPayment().setVisibility(View.VISIBLE);
        bookingView.getRentalPeriodTitle().setVisibility(View.VISIBLE);
        bookingView.getRentalPeriod().setVisibility(View.VISIBLE);

        bookingView.getRenterPhotoCompleted().setVisibility(View.VISIBLE);
        bookingView.getRatingBlock().setVisibility(View.VISIBLE);
        bookingView.getRatingTitle().setVisibility(View.VISIBLE);
        bookingView.getRatingBar().setVisibility(View.VISIBLE);
        bookingView.getRatingEdit().setVisibility(View.VISIBLE);
        if (isRenter) {
            bookingView.getReceiptsContainer().setVisibility(View.VISIBLE);
        } else {
            bookingView.getEarningsContainer().setVisibility(View.VISIBLE);
        }

        bookingView.getRenterNameTitle().setVisibility(View.GONE);
        bookingView.getRenterName().setVisibility(View.GONE);
        bookingView.getRenterPhoto().setVisibility(View.GONE);
        bookingView.getDeliverToTitle().setVisibility(View.GONE);
        bookingView.getDeliverTo().setVisibility(View.GONE);
        bookingView.getHandoverOnTitle().setVisibility(View.GONE);
        bookingView.getHandoverOn().setVisibility(View.GONE);
        bookingView.getReturnByTitle().setVisibility(View.GONE);
        bookingView.getReturnBy().setVisibility(View.GONE);
        bookingView.getHandoverAtTitle().setVisibility(View.GONE);
        bookingView.getHandoverAt().setVisibility(View.GONE);
        bookingView.getTotalCostTitle().setVisibility(View.GONE);
        bookingView.getTotalCost().setVisibility(View.GONE);
        bookingView.getRenterMessage().setVisibility(View.GONE);
        bookingView.getRenterCallTitle().setVisibility(View.GONE);
        bookingView.getRenterCall().setVisibility(View.GONE);
        bookingView.getRenterChatTitle().setVisibility(View.GONE);
        bookingView.getRenterChat().setVisibility(View.GONE);
        bookingView.getCancelMessage().setVisibility(View.GONE);
        bookingView.getAcceptMessage().setVisibility(View.GONE);
        bookingView.getBtnCancel().setVisibility(View.GONE);
        bookingView.getBtnAccept().setVisibility(View.GONE);
    }

    @Override
    public BookingState getType() {
        return BookingState.COMPLETED;
    }
}

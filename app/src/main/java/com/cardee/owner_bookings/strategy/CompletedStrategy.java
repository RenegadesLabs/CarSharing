package com.cardee.owner_bookings.strategy;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.cardee.R;
import com.cardee.domain.bookings.BookingState;
import com.cardee.owner_bookings.view.BookingView;

public class CompletedStrategy extends PresentationStrategy {

    private final BookingView bookingView;

    public CompletedStrategy(@NonNull View view, @NonNull ActionListener listener) {
        super(view, listener);
        bookingView = (BookingView) view;
        int statusColor = ContextCompat.getColor(view.getContext(), R.color.booking_state_completed);

        bookingView.bookingStatus.setBackgroundColor(statusColor);
        bookingView.bookingStatus.setText(R.string.booking_state_completed);
        bookingView.bookingPayment.setVisibility(View.VISIBLE);
        bookingView.rentalPeriodTitle.setVisibility(View.VISIBLE);
        bookingView.rentalPeriod.setVisibility(View.VISIBLE);

        bookingView.renterPhotoCompleted.setVisibility(View.VISIBLE);
        bookingView.ratingBlock.setVisibility(View.VISIBLE);
        bookingView.ratingTitle.setVisibility(View.VISIBLE);
        bookingView.ratingBar.setVisibility(View.VISIBLE);
        bookingView.ratingEdit.setVisibility(View.VISIBLE);
        bookingView.earningsContainer.setVisibility(View.VISIBLE);

        bookingView.renterNameTitle.setVisibility(View.GONE);
        bookingView.renterName.setVisibility(View.GONE);
        bookingView.renterPhoto.setVisibility(View.GONE);
        bookingView.deliverToTitle.setVisibility(View.GONE);
        bookingView.deliverTo.setVisibility(View.GONE);
        bookingView.handoverOnTitle.setVisibility(View.GONE);
        bookingView.handoverOn.setVisibility(View.GONE);
        bookingView.returnByTitle.setVisibility(View.GONE);
        bookingView.returnBy.setVisibility(View.GONE);
        bookingView.handoverAtTitle.setVisibility(View.GONE);
        bookingView.handoverAt.setVisibility(View.GONE);
        bookingView.totalCostTitle.setVisibility(View.GONE);
        bookingView.totalCost.setVisibility(View.GONE);
        bookingView.renterMessage.setVisibility(View.GONE);
        bookingView.renterCallTitle.setVisibility(View.GONE);
        bookingView.renterCall.setVisibility(View.GONE);
        bookingView.renterChatTitle.setVisibility(View.GONE);
        bookingView.renterChat.setVisibility(View.GONE);
        bookingView.cancelMessage.setVisibility(View.GONE);
        bookingView.acceptMessage.setVisibility(View.GONE);
        bookingView.btnCancel.setVisibility(View.GONE);
        bookingView.btnAccept.setVisibility(View.GONE);
    }

    @Override
    public BookingState getType() {
        return BookingState.COMPLETED;
    }
}

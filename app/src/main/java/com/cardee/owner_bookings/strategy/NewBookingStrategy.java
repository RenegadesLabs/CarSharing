package com.cardee.owner_bookings.strategy;


import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.cardee.R;
import com.cardee.domain.bookings.BookingState;
import com.cardee.owner_bookings.view.BookingView;

public class NewBookingStrategy extends PresentationStrategy implements View.OnClickListener {

    private static final String TAG = NewBookingStrategy.class.getSimpleName();

    private final BookingView bookingView;

    public NewBookingStrategy(@NonNull View view, @NonNull ActionListener listener) {
        super(view, listener);
        bookingView = (BookingView) view;
        int statusColor = ContextCompat.getColor(view.getContext(), R.color.booking_state_new);

        bookingView.bookingStatus.setBackgroundColor(statusColor);
        bookingView.bookingStatus.setText(R.string.booking_state_new);
        bookingView.renterNameTitle.setVisibility(View.VISIBLE);
        bookingView.renterName.setVisibility(View.VISIBLE);
        bookingView.renterPhoto.setVisibility(View.VISIBLE);
        bookingView.bookingPayment.setVisibility(View.GONE);
        bookingView.rentalPeriodTitle.setVisibility(View.VISIBLE);
        bookingView.rentalPeriod.setVisibility(View.VISIBLE);
        bookingView.deliverToTitle.setVisibility(View.VISIBLE);
        bookingView.deliverTo.setVisibility(View.VISIBLE);
        bookingView.handoverOnTitle.setVisibility(View.GONE);
        bookingView.handoverOn.setVisibility(View.GONE);
        bookingView.returnByTitle.setVisibility(View.GONE);
        bookingView.returnBy.setVisibility(View.GONE);
        bookingView.handoverAtTitle.setVisibility(View.GONE);
        bookingView.handoverAt.setVisibility(View.GONE);
        bookingView.totalCostTitle.setVisibility(View.VISIBLE);
        bookingView.totalCost.setVisibility(View.VISIBLE);
        bookingView.renterMessage.setVisibility(View.VISIBLE);
        bookingView.renterCallTitle.setVisibility(View.GONE);
        bookingView.renterCall.setVisibility(View.GONE);
        bookingView.renterChatTitle.setVisibility(View.GONE);
        bookingView.renterChat.setVisibility(View.GONE);
        bookingView.cancelMessage.setVisibility(View.GONE);
        bookingView.acceptMessage.setVisibility(View.VISIBLE);
        bookingView.btnCancel.setVisibility(View.VISIBLE);
        bookingView.btnAccept.setVisibility(View.VISIBLE);
        bookingView.renterPhotoCompleted.setVisibility(View.GONE);
        bookingView.ratingBlock.setVisibility(View.GONE);
        bookingView.ratingTitle.setVisibility(View.GONE);
        bookingView.ratingBar.setVisibility(View.GONE);
        bookingView.ratingEdit.setVisibility(View.GONE);

        bookingView.acceptMessage.setText(R.string.booking_message_accept);
        bookingView.btnAccept.setText(R.string.booking_title_accept);
        bookingView.btnCancel.setText(R.string.booking_title_cancel);

        bookingView.renterPhoto.setOnClickListener(this);
        bookingView.btnCancel.setOnClickListener(this);
        bookingView.btnAccept.setOnClickListener(this);
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

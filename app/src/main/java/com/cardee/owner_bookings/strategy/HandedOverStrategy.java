package com.cardee.owner_bookings.strategy;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.cardee.R;
import com.cardee.domain.bookings.BookingState;
import com.cardee.owner_bookings.view.BookingView;

public class HandedOverStrategy extends PresentationStrategy implements View.OnClickListener {

    private static final String TAG = HandedOverStrategy.class.getSimpleName();

    private final BookingView bookingView;

    public HandedOverStrategy(@NonNull View view, @NonNull ActionListener listener) {
        super(view, listener);
        bookingView = (BookingView) view;

        int statusColor = ContextCompat.getColor(view.getContext(), R.color.booking_state_collected);

        bookingView.bookingStatus.setBackgroundColor(statusColor);
        bookingView.bookingStatus.setText(R.string.booking_state_collected);
        bookingView.renterNameTitle.setVisibility(View.VISIBLE);
        bookingView.renterName.setVisibility(View.VISIBLE);
        bookingView.renterPhoto.setVisibility(View.VISIBLE);
        bookingView.bookingPayment.setVisibility(View.VISIBLE);
        bookingView.rentalPeriodTitle.setVisibility(View.GONE);
        bookingView.rentalPeriod.setVisibility(View.GONE);
        bookingView.deliverToTitle.setVisibility(View.GONE);
        bookingView.deliverTo.setVisibility(View.GONE);
        bookingView.handoverOnTitle.setVisibility(View.GONE);
        bookingView.handoverOn.setVisibility(View.GONE);
        bookingView.returnByTitle.setVisibility(View.VISIBLE);
        bookingView.returnBy.setVisibility(View.VISIBLE);
        bookingView.handoverAtTitle.setVisibility(View.GONE);
        bookingView.handoverAt.setVisibility(View.GONE);
        bookingView.totalCostTitle.setVisibility(View.VISIBLE);
        bookingView.totalCost.setVisibility(View.VISIBLE);
        bookingView.renterMessage.setVisibility(View.GONE);
        bookingView.renterCallTitle.setVisibility(View.VISIBLE);
        bookingView.renterCall.setVisibility(View.VISIBLE);
        bookingView.renterChatTitle.setVisibility(View.VISIBLE);
        bookingView.renterChat.setVisibility(View.VISIBLE);
        bookingView.cancelMessage.setVisibility(View.GONE);
        bookingView.acceptMessage.setVisibility(View.VISIBLE);
        bookingView.btnCancel.setVisibility(View.GONE);
        bookingView.btnAccept.setVisibility(View.VISIBLE);
        bookingView.renterPhotoCompleted.setVisibility(View.GONE);
        bookingView.ratingBlock.setVisibility(View.GONE);
        bookingView.ratingTitle.setVisibility(View.GONE);
        bookingView.ratingBar.setVisibility(View.GONE);
        bookingView.ratingEdit.setVisibility(View.GONE);

        bookingView.acceptMessage.setText(R.string.booking_message_handover_complete);
        bookingView.btnAccept.setText(R.string.booking_title_complete);

        bookingView.renterPhoto.setOnClickListener(this);
        bookingView.renterCall.setOnClickListener(this);
        bookingView.renterChat.setOnClickListener(this);
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
                listener.onCompleted();
                break;
            case R.id.renter_call:
                listener.onCall();
                break;
            case R.id.renter_chat:
                listener.onChat();
                break;
        }
    }

    @Override
    public BookingState getType() {
        return BookingState.COLLECTED;
    }
}

package com.cardee.owner_bookings.strategy;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.cardee.R;
import com.cardee.domain.bookings.BookingState;
import com.cardee.owner_bookings.view.BookingView;

public class HandingOverStrategy extends PresentationStrategy implements View.OnClickListener {

    private static final String TAG = HandingOverStrategy.class.getSimpleName();

    private final BookingView bookingView;

    public HandingOverStrategy(@NonNull View view, @NonNull ActionListener listener, boolean isRenter) {
        super(view, listener);
        bookingView = (BookingView) view;
        int statusColor = ContextCompat.getColor(view.getContext(), R.color.booking_state_collecting);

        bookingView.getBookingStatus().setBackgroundColor(statusColor);
        bookingView.getBookingStatus().setText(isRenter ? R.string.booking_state_collecting_renter : R.string.booking_state_collecting);
        bookingView.getRenterNameTitle().setVisibility(View.VISIBLE);
        bookingView.getRenterNameTitle().setText(isRenter ? R.string.booking_owner_title : R.string.booking_request_title);
        bookingView.getRenterName().setVisibility(View.VISIBLE);
        bookingView.getRenterPhoto().setVisibility(View.VISIBLE);
        bookingView.getBookingPayment().setVisibility(View.VISIBLE);
        bookingView.getRentalPeriodTitle().setVisibility(View.GONE);
        bookingView.getRentalPeriod().setVisibility(View.GONE);
        bookingView.getDeliverToTitle().setVisibility(View.GONE);
        bookingView.getDeliverTo().setVisibility(View.GONE);
        bookingView.getHandoverOnTitle().setVisibility(View.GONE);
        bookingView.getHandoverOn().setVisibility(View.GONE);
        bookingView.getReturnByTitle().setVisibility(View.VISIBLE);
        bookingView.getReturnBy().setVisibility(View.VISIBLE);
        bookingView.getHandoverAtTitle().setVisibility(View.GONE);
        bookingView.getHandoverAt().setVisibility(View.GONE);
        bookingView.getTotalCostTitle().setVisibility(View.VISIBLE);
        bookingView.getTotalCost().setVisibility(View.VISIBLE);
        bookingView.getRenterMessage().setVisibility(View.GONE);
        bookingView.getRenterCallTitle().setVisibility(View.VISIBLE);
        bookingView.getRenterCall().setVisibility(View.VISIBLE);
        bookingView.getRenterChatTitle().setVisibility(View.VISIBLE);
        bookingView.getRenterChat().setVisibility(View.VISIBLE);
        bookingView.getCancelMessage().setVisibility(View.VISIBLE);
        bookingView.getAcceptMessage().setVisibility(View.GONE);
        bookingView.getBtnCancel().setVisibility(View.VISIBLE);
        bookingView.getBtnAccept().setVisibility(View.GONE);
        bookingView.getRenterPhotoCompleted().setVisibility(View.GONE);
        bookingView.getRatingBlock().setVisibility(View.GONE);
        bookingView.getRatingTitle().setVisibility(View.GONE);
        bookingView.getRatingBar().setVisibility(View.GONE);
        bookingView.getRatingEdit().setVisibility(View.GONE);

        bookingView.getCancelMessage().setText(isRenter ? R.string.booking_message_handover_wait_renter : R.string.booking_message_handover_wait);
        bookingView.getBtnCancel().setText(isRenter ? R.string.booking_title_cancel_handover_renter : R.string.booking_title_cancel_handover);

        bookingView.getRenterPhoto().setOnClickListener(this);
        bookingView.getRenterCall().setOnClickListener(this);
        bookingView.getRenterChat().setOnClickListener(this);
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
            case R.id.btn_cancel:
                listener.onCancelHandOver();
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
        return BookingState.COLLECTING;
    }
}

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

        bookingView.bookingStatus.setBackgroundColor(statusColor);
        bookingView.bookingStatus.setText(R.string.booking_state_canceled);
        bookingView.renterNameTitle.setVisibility(View.VISIBLE);
        bookingView.renterName.setVisibility(View.VISIBLE);
        bookingView.renterPhoto.setVisibility(View.VISIBLE);
        bookingView.bookingPayment.setVisibility(View.GONE);
        bookingView.rentalPeriodTitle.setVisibility(View.GONE);
        bookingView.rentalPeriod.setVisibility(View.GONE);
        bookingView.deliverToTitle.setVisibility(View.GONE);
        bookingView.deliverTo.setVisibility(View.GONE);
        bookingView.handoverOnTitle.setVisibility(View.GONE);
        bookingView.handoverOn.setVisibility(View.GONE);
        bookingView.returnByTitle.setVisibility(View.GONE);
        bookingView.returnBy.setVisibility(View.GONE);
        bookingView.handoverAtTitle.setVisibility(View.GONE);
        bookingView.handoverAt.setVisibility(View.GONE);
        bookingView.totalCostTitle.setVisibility(View.VISIBLE);
        bookingView.totalCost.setVisibility(View.VISIBLE);
        bookingView.renterMessage.setVisibility(View.GONE);
        bookingView.renterCallTitle.setVisibility(View.GONE);
        bookingView.renterCall.setVisibility(View.GONE);
        bookingView.renterChatTitle.setVisibility(View.GONE);
        bookingView.renterChat.setVisibility(View.GONE);
        bookingView.cancelMessage.setVisibility(View.GONE);
        bookingView.acceptMessage.setVisibility(View.GONE);
        bookingView.btnCancel.setVisibility(View.GONE);
        bookingView.btnAccept.setVisibility(View.GONE);
        bookingView.renterPhotoCompleted.setVisibility(View.GONE);
        bookingView.ratingBlock.setVisibility(View.GONE);
        bookingView.ratingTitle.setVisibility(View.GONE);
        bookingView.ratingBar.setVisibility(View.GONE);
        bookingView.ratingEdit.setVisibility(View.GONE);
        bookingView.renterPhoto.setOnClickListener(this);
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

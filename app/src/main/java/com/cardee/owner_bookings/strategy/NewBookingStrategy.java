package com.cardee.owner_bookings.strategy;


import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.cardee.R;
import com.cardee.owner_bookings.view.BookingView;

public class NewBookingStrategy extends PresentationStrategy implements View.OnClickListener {

    private static final String TAG = NewBookingStrategy.class.getSimpleName();

    private final BookingView bookingView;

    protected NewBookingStrategy(@NonNull View view, @NonNull ActionListener listener) {
        super(view, listener);
        bookingView = (BookingView) view;
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

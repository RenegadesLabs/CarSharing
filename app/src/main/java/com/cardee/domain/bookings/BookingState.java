package com.cardee.domain.bookings;


import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.cardee.R;

public enum BookingState {

    NEW(R.string.booking_state_new, R.color.booking_state_new),
    CANCELED(R.string.booking_state_canceled, R.color.booking_state_canceled),
    CONFIRMED(R.string.booking_state_confirmed, R.color.booking_state_confirmed),
    COLLECTING(R.string.booking_state_collecting, R.color.booking_state_collecting),
    COLLECTED(R.string.booking_state_collected, R.color.booking_state_collected),
    COMPLETED(R.string.booking_state_completed, R.color.booking_state_completed);

    private @StringRes
    int titleId;
    private @ColorRes
    int colorId;

    BookingState(int titleId, int colorId) {
        this.titleId = titleId;
        this.colorId = colorId;
    }

    public int getTitleId() {
        return titleId;
    }

    public int getColorId() {
        return colorId;
    }
}

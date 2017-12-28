package com.cardee.domain.bookings;


import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.cardee.R;

public enum BookingState {

    NEW("new", R.string.booking_state_new, R.color.booking_state_new),
    CANCELED("canceled", R.string.booking_state_canceled, R.color.booking_state_canceled),
    CONFIRMED("confirmed", R.string.booking_state_confirmed, R.color.booking_state_confirmed),
    COLLECTING("collecting", R.string.booking_state_collecting, R.color.booking_state_collecting),
    COLLECTED("collected", R.string.booking_state_collected, R.color.booking_state_collected),
    COMPLETED("completed", R.string.booking_state_completed, R.color.booking_state_completed);
    public final String value;
    private @StringRes
    final int titleId;
    private @ColorRes
    final int colorId;

    BookingState(String value, int titleId, int colorId) {
        this.value = value;
        this.titleId = titleId;
        this.colorId = colorId;
    }

    public String getValue() {
        return value;
    }

    public int getTitleId() {
        return titleId;
    }

    public int getColorId() {
        return colorId;
    }
}

package com.cardee.domain.bookings;


import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.cardee.R;
import com.cardee.data_source.remote.api.booking.request.State;

public enum BookingState {

    NEW("new", R.string.booking_state_new, R.color.booking_state_new, null),
    CANCELED("canceled", R.string.booking_state_canceled, R.color.booking_state_canceled, State.CANCEL),
    CONFIRMED("confirmed", R.string.booking_state_confirmed, R.color.booking_state_confirmed, State.CONFIRM),
    COLLECTING("collecting", R.string.booking_state_collecting, R.color.booking_state_collecting, State.COLLECTING),
    COLLECTED("collected", R.string.booking_state_collected, R.color.booking_state_collected, State.COLLECT),
    COMPLETED("completed", R.string.booking_state_completed, R.color.booking_state_completed, State.COMPLETE);
    public final String value;
    private @StringRes
    final int titleId;
    private @ColorRes
    final int colorId;
    private final String request;

    BookingState(String value, int titleId, int colorId, String request) {
        this.value = value;
        this.titleId = titleId;
        this.colorId = colorId;
        this.request = request;
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

    public String getRequest() {
        return request;
    }

    public static BookingState fromRequest(String request) {
        switch (request) {
            case State.CANCEL:
                return CANCELED;
            case State.CONFIRM:
                return CONFIRMED;
            case State.COLLECTING:
                return COLLECTING;
            case State.COLLECT:
                return COLLECTED;
            case State.COMPLETE:
                return COMPLETED;
        }
        return null;
    }
}

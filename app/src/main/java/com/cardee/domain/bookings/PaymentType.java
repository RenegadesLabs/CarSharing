package com.cardee.domain.bookings;


import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.cardee.R;

public enum PaymentType {

    CARD(R.string.booking_payment_card, R.color.payment_card),
    CASH(R.string.booking_payment_cash, R.color.payment_cash);

    private int titleId;
    private int bgId;

    PaymentType(@StringRes int titleId, @ColorRes int bgId) {
        this.titleId = titleId;
        this.bgId = bgId;
    }

    public int getBgId() {
        return bgId;
    }

    public int getTitleId() {
        return titleId;
    }
}

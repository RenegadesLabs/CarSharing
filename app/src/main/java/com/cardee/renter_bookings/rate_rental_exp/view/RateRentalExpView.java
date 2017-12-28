package com.cardee.renter_bookings.rate_rental_exp.view;


import android.text.Spannable;

import com.cardee.mvp.BaseView;

public interface RateRentalExpView extends BaseView {

    void onSendRateSuccess();

    void setCarTitle(Spannable text);

    void setRentalPeriod(String period);

    void setCarPhoto(String link);

    void setOwnerPhoto(String ownerPhoto);
}

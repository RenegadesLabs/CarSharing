package com.cardee.owner_bookings.car_returned.view;

import android.text.Spannable;

import com.cardee.mvp.BaseView;

public interface CarReturnedView extends BaseView {


    void setCarTitle(Spannable text);

    void onSendCommentSuccess();

    void setCarNumber(String plateNumber);

    void setBookingDate(String dateCreated);

    void setRentalPeriod(String period);

    void setCarPhoto(String link);

    void setRenterPhoto(String renterPhoto);

    void setRenterName(String renterName);

    void setCommentHint(String hint);
}

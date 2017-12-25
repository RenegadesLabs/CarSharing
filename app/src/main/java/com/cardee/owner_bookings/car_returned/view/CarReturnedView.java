package com.cardee.owner_bookings.car_returned.view;

import android.text.Spannable;

import com.cardee.mvp.BaseView;

public interface CarReturnedView extends BaseView {


    void setCarTitle(Spannable text);

    void onSendCommentSuccess();
}

package com.cardee.renter_notifications.view;


import com.cardee.mvp.BaseView;

public interface RenterNotifView extends BaseView {

    void setHandoverReminder(String s);

    void setReturnReminder(String s);
}

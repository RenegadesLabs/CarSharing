package com.cardee.owner_notifications.view;


import com.cardee.mvp.BaseView;

public interface OwnerNotifView extends BaseView {
    void setHandoverReminder(String s);

    void setReturnReminder(String s);
}

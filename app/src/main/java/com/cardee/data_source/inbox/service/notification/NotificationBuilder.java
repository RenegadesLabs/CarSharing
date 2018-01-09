package com.cardee.data_source.inbox.service.notification;

import android.content.Context;

import com.cardee.data_source.inbox.service.model.Notification;

public interface NotificationBuilder {

    void createNotification(Context context, Notification notification);

    void showNotification(Context context);

}

package com.cardee.data_source.inbox.service.notification;

import android.content.Context;

import com.cardee.data_source.inbox.service.model.BaseNotification;

import java.util.Map;

public interface NotificationBuilder {

    void createNotification(Context context, BaseNotification baseNotification);

    void showNotification(Context context);

}

package com.cardee.data_source.inbox.service.notification;

import android.content.Context;

import java.util.Map;

public interface NotificationBuilder {

    void createNotification(Context context, Map<String, String> messageData);

    void showNotification(Context context);

}

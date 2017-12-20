package com.cardee.inbox.service.notification;

import android.content.Context;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public interface NotificationBuilder {

    void createNotification(Context context, Map<String, String> messageData);

    void showNotification(Context context);

}

package com.cardee.data_source.inbox.service;

import com.cardee.data_source.inbox.service.controller.FcmRepositoryController;
import com.cardee.data_source.inbox.service.controller.RepositoryController;
import com.cardee.data_source.inbox.service.notification.FcmNotificationBuilder;
import com.cardee.data_source.inbox.service.notification.NotificationBuilder;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class InboxMessagingService extends FirebaseMessagingService {

    private RepositoryController mRepositoryController;
    private NotificationBuilder mNotificationBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
        mRepositoryController = new FcmRepositoryController();
        mNotificationBuilder = new FcmNotificationBuilder();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() == null) return;

        mRepositoryController.updateInbox(remoteMessage, notificationData -> {
            mNotificationBuilder.createNotification(this, notificationData);
            mNotificationBuilder.showNotification(this);
        });
    }
}

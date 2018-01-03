package com.cardee.data_source.inbox.service.model;

public interface BaseNotification {

    enum NotificationType {
        CHAT, ALERT
    }

    NotificationType getNotificationType();

    String getContentTitle();

    String getContentText();
}

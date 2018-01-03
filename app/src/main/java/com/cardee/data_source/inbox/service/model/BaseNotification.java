package com.cardee.data_source.inbox.service.model;

public interface BaseNotification {

    enum NotificationType {
        CHAT, ALERT
    }

    int getId();

    String getAttachment();

    int getUnreadCount();

    NotificationType getNotificationType();

    String getContentTitle();

    String getContentText();

    boolean isCurrentSessionNeedToNotify();

    boolean isOwnerSession();
}

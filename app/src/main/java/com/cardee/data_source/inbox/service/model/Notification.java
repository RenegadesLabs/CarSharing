package com.cardee.data_source.inbox.service.model;

public interface Notification {

    enum NotificationType {
        CHAT, ALERT
    }

    int getId();

    String getAttachment();

    int getUnreadCount();

    NotificationType getNotificationType();

    String getContentTitle();

    String getContentText();

    int getType();

    boolean isCurrentSessionNeedToNotify();

    boolean isOwnerSession();

    interface ChatNotification extends Notification {

    }

    interface AlertNotification extends Notification {

    }
}

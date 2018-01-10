package com.cardee.data_source.inbox.service.model;

import com.cardee.data_source.inbox.local.alert.entity.Alert;

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

    Alert.Type getType();

    boolean isCurrentSessionNeedToNotify();

    boolean isOwnerSession();

    interface ChatNotification extends Notification {

    }

    interface AlertNotification extends Notification {

    }
}

package com.cardee.data_source.inbox.service.model;

public class AlertNotification implements BaseNotification {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getAttachment() {
        return null;
    }

    @Override
    public int getUnreadCount() {
        return 0;
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.ALERT;
    }

    @Override
    public String getContentTitle() {
        return null;
    }

    @Override
    public String getContentText() {
        return null;
    }

    @Override
    public boolean isCurrentSessionNeedToNotify() {
        return false;
    }

    @Override
    public boolean isOwnerSession() {
        return false;
    }
}

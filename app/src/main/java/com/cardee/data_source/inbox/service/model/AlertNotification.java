package com.cardee.data_source.inbox.service.model;

public class AlertNotification implements BaseNotification {

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
}

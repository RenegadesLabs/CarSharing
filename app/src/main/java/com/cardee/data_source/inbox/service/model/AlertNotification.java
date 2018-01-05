package com.cardee.data_source.inbox.service.model;

import com.cardee.data_source.remote.service.AccountManager;

public class AlertNotification implements Notification {

    private Integer alertId;
    private Integer objectId;
    private String alertType;
    private String alertTitle;
    private String alertMessage;
    private String alertDate;
    private String dateCreated;
    private String alertState;
    private Integer unreadAlertCount;

    private String alertAttachment;
    private Boolean isCurrentSessionNeedToNotify;


    @Override
    public NotificationType getNotificationType() {
        return NotificationType.ALERT;
    }

    @Override
    public int getId() {
        return alertId;
    }

    @Override
    public String getAttachment() {
        return alertAttachment;
    }

    @Override
    public int getUnreadCount() {
        return unreadAlertCount;
    }

    @Override
    public String getContentTitle() {
        return alertTitle == null ? "" : alertTitle;
    }

    @Override
    public String getContentText() {
        return alertMessage == null ? "" : alertMessage;
    }

    @Override
    public boolean isCurrentSessionNeedToNotify() {
        return isCurrentSessionNeedToNotify;
    }

    @Override
    public boolean isOwnerSession() {
        return alertAttachment.equals(AccountManager.OWNER_SESSION);
    }


    public Integer getAlertId() {
        return alertId;
    }

    public void setAlertId(Integer alertId) {
        this.alertId = alertId;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(String alertDate) {
        this.alertDate = alertDate;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setUnreadAlertCount(Integer unreadAlertCount) {
        this.unreadAlertCount = unreadAlertCount;
    }

    public String getAlertAttachment() {
        return alertAttachment;
    }

    public void setAlertAttachment(String alertAttachment) {
        this.alertAttachment = alertAttachment;
    }

    public Boolean getCurrentSessionNeedToNotify() {
        return isCurrentSessionNeedToNotify;
    }

    public void setCurrentSessionNeedToNotify(Boolean currentSessionNeedToNotify) {
        isCurrentSessionNeedToNotify = currentSessionNeedToNotify;
    }

    public String getAlertState() {
        return alertState;
    }

    public void setAlertState(String alertState) {
        this.alertState = alertState;
    }
}

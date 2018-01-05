package com.cardee.data_source.inbox.service.model;

import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.remote.chat.RemoteData;
import com.cardee.data_source.remote.service.AccountManager;

public class AlertNotification implements Notification {

    private Integer alertId;
    private Integer objectId;
    private Integer alertType;
    private String alertTitle;
    private String alertMessage;
    private String alertDate;
    private String dateCreated;
    private Boolean alertStatus;
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
    public int getType() {
        return alertType;
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

    public Integer getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = convertNotificationType(alertType);
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

    public Boolean getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(String alertStatus) {
        this.alertStatus = convertState(alertStatus);
    }

    private boolean convertState(String remoteStatus) {
        return remoteStatus.equals(RemoteData.NotificationType.NEW);
    }

    private int convertNotificationType(String remoteType) {
        int notificationType;

        switch (remoteType) {
            case RemoteData.NotificationType.NEW_REQUEST:
            case RemoteData.NotificationType.ACCEPTED:
            case RemoteData.NotificationType.BOOKING_EXT:
            case RemoteData.NotificationType.OWNER_CHECKLIST_UPD:
            case RemoteData.NotificationType.RENTER_CHECKLIST_UPD:
            case RemoteData.NotificationType.INIT_CHECKLIST:
                notificationType = Alert.TYPE_BOOKING;
                break;
            case RemoteData.NotificationType.HANDOVER_REMINDER:
            case RemoteData.NotificationType.RETURN_REMINDER:
            case RemoteData.NotificationType.RENTER_REVIEW_REMINDER:
            case RemoteData.NotificationType.OWNER_REVIEW_REMINDER:
                notificationType = Alert.TYPE_REMINDER;
                break;
            case RemoteData.NotificationType.RETURN_OVERDUE:
            case RemoteData.NotificationType.REQUEST_EXPIRED:
            case RemoteData.NotificationType.BOOKING_CANCELLATION:
                notificationType = Alert.TYPE_OVERDUE;
                break;
            case RemoteData.NotificationType.OWNER_REVIEW:
            case RemoteData.NotificationType.RENTER_REVIEW:
                notificationType = Alert.TYPE_REVIEWS;
                break;
            case RemoteData.NotificationType.SYSTEM_MESSAGES:
            case RemoteData.NotificationType.CAR_VERIFICATION:
            case RemoteData.NotificationType.USER_VERIFICATION:
            case RemoteData.NotificationType.RENTER_STATE_CHANGE:
            case RemoteData.NotificationType.OWNER_STATE_CHANGE:
            case RemoteData.NotificationType.CAR_STATE_CHANGE:
            case RemoteData.NotificationType.BROADCAST:
                notificationType = Alert.TYPE_SYSTEM;
                break;
            default:
                notificationType = Alert.TYPE_SYSTEM;
        }
        return notificationType;
    }
}

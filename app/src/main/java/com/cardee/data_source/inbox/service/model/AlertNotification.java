package com.cardee.data_source.inbox.service.model;

import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.remote.chat.RemoteData;
import com.cardee.data_source.remote.service.AccountManager;

public class AlertNotification implements Notification {

    private Integer alertId;
    private Integer objectId;
    private String alertType;
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
    public Alert.Type getType() {
        return Alert.Type.valueOf(getAlertType());
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

    private String convertNotificationType(String remoteType) {
        String notificationType;

        switch (remoteType) {
            case RemoteData.NotificationType.NEW_REQUEST:
                notificationType = Alert.Type.NEW_REQUEST.toString();
                break;
            case RemoteData.NotificationType.ACCEPTED:
                notificationType = Alert.Type.ACCEPTED.toString();
                break;
            case RemoteData.NotificationType.BOOKING_EXT:
                notificationType = Alert.Type.BOOKING_EXT.toString();
                break;
            case RemoteData.NotificationType.OWNER_CHECKLIST_UPD:
                notificationType = Alert.Type.OWNER_CHECKLIST_UPD.toString();
                break;
            case RemoteData.NotificationType.RENTER_CHECKLIST_UPD:
                notificationType = Alert.Type.RENTER_CHECKLIST_UPD.toString();
                break;
            case RemoteData.NotificationType.INIT_CHECKLIST:
                notificationType = Alert.Type.INIT_CHECKLIST.toString();
                break;
            case RemoteData.NotificationType.HANDOVER_REMINDER:
                notificationType = Alert.Type.HANDOVER_REMINDER.toString();
                break;
            case RemoteData.NotificationType.RETURN_REMINDER:
                notificationType = Alert.Type.RETURN_REMINDER.toString();
                break;
            case RemoteData.NotificationType.RENTER_REVIEW_REMINDER:
                notificationType = Alert.Type.RENTER_REVIEW_REMINDER.toString();
                break;
            case RemoteData.NotificationType.OWNER_REVIEW_REMINDER:
                notificationType = Alert.Type.OWNER_REVIEW_REMINDER.toString();
                break;
            case RemoteData.NotificationType.RETURN_OVERDUE:
                notificationType = Alert.Type.RETURN_OVERDUE.toString();
                break;
            case RemoteData.NotificationType.REQUEST_EXPIRED:
                notificationType = Alert.Type.REQUEST_EXPIRED.toString();
                break;
            case RemoteData.NotificationType.BOOKING_CANCELLATION:
                notificationType = Alert.Type.BOOKING_CANCELLATION.toString();
                break;
            case RemoteData.NotificationType.OWNER_REVIEW:
                notificationType = Alert.Type.OWNER_REVIEW.toString();
                break;
            case RemoteData.NotificationType.RENTER_REVIEW:
                notificationType = Alert.Type.RENTER_REVIEW.toString();
                break;
            case RemoteData.NotificationType.SYSTEM_MESSAGES:
                notificationType = Alert.Type.SYSTEM_MESSAGES.toString();
                break;
            case RemoteData.NotificationType.CAR_VERIFICATION:
                notificationType = Alert.Type.CAR_VERIFICATION.toString();
                break;
            case RemoteData.NotificationType.USER_VERIFICATION:
                notificationType = Alert.Type.USER_VERIFICATION.toString();
                break;
            case RemoteData.NotificationType.RENTER_STATE_CHANGE:
                notificationType = Alert.Type.RENTER_STATE_CHANGE.toString();
                break;
            case RemoteData.NotificationType.OWNER_STATE_CHANGE:
                notificationType = Alert.Type.OWNER_STATE_CHANGE.toString();
                break;
            case RemoteData.NotificationType.CAR_STATE_CHANGE:
                notificationType = Alert.Type.CAR_STATE_CHANGE.toString();
                break;
            case RemoteData.NotificationType.BROADCAST:
                notificationType = Alert.Type.BROADCAST.toString();
                break;
            default:
                notificationType = Alert.Type.SYSTEM_MESSAGES.toString();
        }
        return notificationType;
    }
}

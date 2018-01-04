package com.cardee.data_source.inbox.remote.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlertRemote {

    @Expose
    @SerializedName("user_alert_id")
    private Integer alertId;
    @Expose
    @SerializedName("date_created")
    private String dateCreated;
    @Expose
    @SerializedName("type_notification")
    private String notificationType;
    @Expose
    @SerializedName("notification_title")
    private String notificationTitle;
    @Expose
    @SerializedName("notification_text")
    private String notificationText;
    @Expose
    @SerializedName("state_notification")
    private String notificationState;

    public AlertRemote() {
    }

    public Integer getAlertId() {
        return alertId;
    }

    public void setAlertId(Integer alertId) {
        this.alertId = alertId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationState() {
        return notificationState;
    }

    public void setNotificationState(String notificationState) {
        this.notificationState = notificationState;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }
}

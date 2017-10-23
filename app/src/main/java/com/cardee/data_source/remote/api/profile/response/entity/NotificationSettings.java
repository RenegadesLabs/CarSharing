package com.cardee.data_source.remote.api.profile.response.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationSettings {

    @Expose
    @SerializedName("notification_id")
    private Integer notificationId;
    @Expose
    @SerializedName("notification_name")
    private String notificationName;
    @Expose
    @SerializedName("notification_goal")
    private String notificationTarget;
    @Expose
    @SerializedName("is_active_email_notification")
    private Boolean emailNotificationActive;
    @Expose
    @SerializedName("is_active_push_notification")
    private Boolean pushNotificationActive;
    @Expose
    @SerializedName("order_num")
    private Integer orderSum;

    public NotificationSettings() {

    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
    }

    public String getNotificationTarget() {
        return notificationTarget;
    }

    public void setNotificationTarget(String notificationTarget) {
        this.notificationTarget = notificationTarget;
    }

    public Boolean getEmailNotificationActive() {
        return emailNotificationActive;
    }

    public void setEmailNotificationActive(Boolean emailNotificationActive) {
        this.emailNotificationActive = emailNotificationActive;
    }

    public Boolean getPushNotificationActive() {
        return pushNotificationActive;
    }

    public void setPushNotificationActive(Boolean pushNotificationActive) {
        this.pushNotificationActive = pushNotificationActive;
    }

    public Integer getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(Integer orderSum) {
        this.orderSum = orderSum;
    }
}

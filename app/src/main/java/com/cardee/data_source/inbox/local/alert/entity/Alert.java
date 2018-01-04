package com.cardee.data_source.inbox.local.alert.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "alerts")
public class Alert {

    public static final String ALERT_SERVER_ID = "alert_id";
    public static final String ALERT_ATTACHMENT = "alert_attachment";

    public static final int TYPE_BOOKING = 20; // bookings, checklist, request accepted,  extension request
    public static final int TYPE_OVERDUE = 21; // cancel, request expired
    public static final int TYPE_REMINDER = 22;
    public static final int TYPE_SYSTEM = 23; // admin, broadcast, state, verification
    public static final int TYPE_REVIEWS = 24; //

    @PrimaryKey
    @ColumnInfo(name = "alert_id")
    private Integer alertId;

    @ColumnInfo(name = "attachment")
    private String attachment;

    @ColumnInfo(name = "date_created")
    private String dateCreated;

    @ColumnInfo(name = "notification_type")
    private Integer notificationType;

    @ColumnInfo(name = "notification_text")
    private String notificationText;

    @ColumnInfo(name = "state")
    private Boolean isNewBooking;


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

    public Integer getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(Integer notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public Boolean getNewBooking() {
        return isNewBooking;
    }

    public void setNewBooking(Boolean newBooking) {
        isNewBooking = newBooking;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}

package com.cardee.data_source.inbox.local.alert.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "alerts")
public class Alert {

    public static final String ALERT_SERVER_ID = "alert_id";
    public static final String ALERT_DESTINATION_ID = "destination_id";
    public static final String ALERT_ATTACHMENT = "alert_attachment";

    public static final int TYPE_BOOKING = 20; // bookings, checklist, request accepted,  extension request
    public static final int TYPE_OVERDUE = 21; // cancel, request expired
    public static final int TYPE_REMINDER = 22;
    public static final int TYPE_SYSTEM = 23; // admin, broadcast, state, verification
    public static final int TYPE_REVIEWS = 24; //

    @PrimaryKey
    @ColumnInfo(name = "alert_id")
    private Integer alertId;

    @ColumnInfo(name = "object_id")
    private Integer objectId;

    @ColumnInfo(name = "alert_type")
    private Integer alertType;

    @ColumnInfo(name = "attachment")
    private String attachment;

    @ColumnInfo(name = "date_created")
    private String dateCreated;

    @ColumnInfo(name = "alert_title")
    private String notificationTitle;

    @ColumnInfo(name = "alert_text")
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

    public Integer getAlertType() {
        return alertType;
    }

    public void setAlertType(Integer alertType) {
        this.alertType = alertType;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
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

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public static class Builder {

        private Alert mAlert;

        public Builder() {
            mAlert = new Alert();
        }

        public Builder withId(int alertId) {
            mAlert.alertId = alertId;
            return this;
        }

        public Builder withObject(int objectId) {
            mAlert.objectId = objectId;
            return this;
        }

        public Builder withAttachment(String attachment) {
            mAlert.attachment = attachment;
            return this;
        }

        public Builder withDateCreated(String dateCreated) {
            mAlert.dateCreated = dateCreated;
            return this;
        }

        public Builder withNotificationType(int notificationType) {
            mAlert.alertType = notificationType;
            return this;
        }

        public Builder withNotificationTitle(String notificationTitle) {
            mAlert.notificationTitle = notificationTitle;
            return this;
        }

        public Builder withNotificationText(String notificationText) {
            mAlert.notificationText = notificationText;
            return this;
        }

        public Builder withStatus(Boolean isNewBooking) {
            mAlert.isNewBooking = isNewBooking;
            return this;
        }

        public Alert build() {
            return mAlert;
        }
    }
}

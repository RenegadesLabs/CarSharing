package com.cardee.data_source.inbox.local.alert.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "alerts")
public class Alert {

    public static final String ALERT_SERVER_ID = "alert_id";
    public static final String ALERT_OBJECT_ID = "object_id";
    public static final String ALERT_ATTACHMENT = "alert_attachment";
    public static final String ALERT_TYPE = "alert_type";

    public enum Type {
        NEW_REQUEST,
        HANDOVER_REMINDER,
        RETURN_REMINDER,
        RETURN_OVERDUE,
        REQUEST_EXPIRED,
        SYSTEM_MESSAGES,
        ACCEPTED,
        CAR_VERIFICATION,
        USER_VERIFICATION,
        RENTER_STATE_CHANGE,
        OWNER_STATE_CHANGE,
        CAR_STATE_CHANGE,
        BOOKING_EXT,
        BOOKING_CANCELLATION,
        RENTER_REVIEW_REMINDER,
        OWNER_CHECKLIST_UPD,
        RENTER_CHECKLIST_UPD,
        BROADCAST,
        INIT_CHECKLIST,
        OWNER_REVIEW_REMINDER,
        RENTER_REVIEW,
        OWNER_REVIEW,
        EXTENSION_REQUEST,
        EXTENSION_CANCELED,
        EXTENSION_APPROVED
    }

    @PrimaryKey
    @ColumnInfo(name = "alert_id")
    private Integer alertId;

    @ColumnInfo(name = "object_id")
    private Integer objectId;

    @ColumnInfo(name = "alert_type")
    private String stringAlertType;

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

    public String getStringAlertType() {
        return stringAlertType;
    }

    public void setStringAlertType(String stringAlertType) {
        this.stringAlertType = stringAlertType;
    }

    public Alert.Type getAlertType() {
        return Alert.Type.valueOf(getStringAlertType());
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

        public Builder withNotificationType(String notificationType) {
            mAlert.stringAlertType = notificationType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alert alert = (Alert) o;

        if (!dateCreated.equals(alert.dateCreated)) return false;
        if (!notificationText.equals(alert.notificationText)) return false;
        return isNewBooking.equals(alert.isNewBooking);
    }

    @Override
    public int hashCode() {
        int result = dateCreated.hashCode();
        result = 31 * result + notificationText.hashCode();
        result = 31 * result + isNewBooking.hashCode();
        return result;
    }
}

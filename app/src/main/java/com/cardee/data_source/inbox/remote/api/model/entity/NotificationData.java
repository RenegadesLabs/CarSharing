package com.cardee.data_source.inbox.remote.api.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.cardee.data_source.remote.service.AccountManager.OWNER_SESSION;
import static com.cardee.data_source.remote.service.AccountManager.RENTER_SESSION;

public class NotificationData {

    @Expose
    @SerializedName("owner_messages_cnt")
    private Integer ownerChatMessages;
    @Expose
    @SerializedName("renter_messages_cnt")
    private Integer renterChatMessages;
    @Expose
    @SerializedName("owner_alerts_cnt")
    private Integer ownerBookingMessages;
    @Expose
    @SerializedName("renter_alerts_cnt")
    private Integer renterBookingMessages;

    private transient String currentSession;

    public NotificationData() {
    }

    public NotificationData(String currentSession) {
        this.currentSession = currentSession;
    }

    public Integer getOwnerChatMessages() {
        return ownerChatMessages == null ? 0 : ownerChatMessages;
    }

    public void setOwnerChatMessages(Integer ownerChatMessages) {
        this.ownerChatMessages = ownerChatMessages;
    }

    public Integer getRenterChatMessages() {
        return renterChatMessages == null ? 0 : renterChatMessages;
    }

    public void setRenterChatMessages(Integer renterChatMessages) {
        this.renterChatMessages = renterChatMessages;
    }

    public Integer getOwnerBookingMessages() {
        return ownerBookingMessages == null ? 0 : ownerBookingMessages;
    }

    public void setOwnerBookingMessages(Integer ownerBookingMessages) {
        this.ownerBookingMessages = ownerBookingMessages;
    }

    public Integer getRenterBookingMessages() {
        return renterBookingMessages == null ? 0 : renterBookingMessages;
    }

    public void setRenterBookingMessages(Integer renterBookingMessages) {
        this.renterBookingMessages = renterBookingMessages;
    }

    public String getCurrentSession() {
        return currentSession;
    }

    public boolean isMissingAlertsExist() {
        if (isOwnerSession()) return ownerBookingMessages > 0;
        else return renterBookingMessages > 0;
    }

    public boolean isMissingChatsExist() {
        if (isOwnerSession()) return ownerChatMessages > 0;
        else return renterChatMessages > 0;
    }

    public int getTotalNotifications() {
        if (isOwnerSession()) return getTotalOwnerNotifications();
        else return getTotalRenterNotifications();
    }

    public void setRelevantAlertCount(Integer bookingCount) {
        if (isOwnerSession()) ownerBookingMessages = bookingCount;
        else renterBookingMessages = bookingCount;
    }

    public void setRelevantChatCount(Integer chatCount) {
        if (isOwnerSession()) ownerChatMessages = chatCount;
        else renterChatMessages = chatCount;
    }

    public void updateChatUnreadCount(Integer unreadCount) {
        if (isOwnerSession()) ownerChatMessages = ownerChatMessages - unreadCount;
        else renterChatMessages = renterChatMessages - unreadCount;
    }

    private boolean isOwnerSession() {
        return currentSession.equals(OWNER_SESSION);
    }

    private int getTotalOwnerNotifications() {
        return ownerChatMessages + ownerBookingMessages;
    }

    private int getTotalRenterNotifications() {
        return renterChatMessages + renterBookingMessages;
    }
}

package com.cardee.data_source.inbox.remote.api.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.cardee.data_source.remote.service.AccountManager.OWNER_SESSION;

public class NotificationData {

    @Expose
    @SerializedName("owner_messages_cnt")
    private Integer ownerChatMessages;
    @Expose
    @SerializedName("renter_messages_cnt")
    private Integer renterChatMessages;
    @Expose
    @SerializedName("owner_alerts_cnt")
    private Integer ownerAlertMessages;
    @Expose
    @SerializedName("renter_alerts_cnt")
    private Integer renterAlertMessages;

    private transient String currentAttachment;

    public NotificationData() {
    }


    public NotificationData(String currentAttachment) {
        this.currentAttachment = currentAttachment;
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

    public Integer getOwnerAlertMessages() {
        return ownerAlertMessages == null ? 0 : ownerAlertMessages;
    }

    public void setOwnerAlertMessages(Integer ownerAlertMessages) {
        this.ownerAlertMessages = ownerAlertMessages;
    }

    public Integer getRenterAlertMessages() {
        return renterAlertMessages == null ? 0 : renterAlertMessages;
    }

    public void setRenterAlertMessages(Integer renterAlertMessages) {
        this.renterAlertMessages = renterAlertMessages;
    }

    public String getCurrentAttachment() {
        return currentAttachment;
    }

    public boolean isMissingAlertsExist() {
        if (isOwnerSession()) return ownerAlertMessages > 0;
        else return renterAlertMessages > 0;
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
        if (isOwnerSession()) ownerAlertMessages = bookingCount;
        else renterAlertMessages = bookingCount;
    }

    public void setRelevantChatCount(Integer chatCount) {
        if (isOwnerSession()) ownerChatMessages = chatCount;
        else renterChatMessages = chatCount;
    }

    public void updateChatUnreadCount(Integer unreadCount) {
        if (isOwnerSession()) ownerChatMessages = ownerChatMessages - unreadCount;
        else renterChatMessages = renterChatMessages - unreadCount;
    }

    public void updateAlertUnreadCount(Integer unreadCount) {
        if (isOwnerSession()) ownerAlertMessages = ownerAlertMessages - unreadCount;
        else renterAlertMessages = renterAlertMessages - unreadCount;
    }

    private boolean isOwnerSession() {
        return currentAttachment.equals(OWNER_SESSION);
    }

    private int getTotalOwnerNotifications() {
        return ownerChatMessages + ownerAlertMessages;
    }

    private int getTotalRenterNotifications() {
        return renterChatMessages + renterAlertMessages;
    }
}

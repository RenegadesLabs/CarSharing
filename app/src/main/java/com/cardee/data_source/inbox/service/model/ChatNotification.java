package com.cardee.data_source.inbox.service.model;

import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.remote.service.AccountManager;

public class ChatNotification implements Notification {

    //chat info
    private Integer chatId;
    private String chatAttachment;
    private Integer unreadMessageCount;
    private Integer unreadChatCount;

    //message info
    private Integer messageId;
    private String messageText;
    private String messageTime;
    private String senderName;
    private Boolean isRead;

    private Boolean isCurrentMessagingSession;
    private Boolean isCurrentSessionNeedToNotify;

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.CHAT;
    }

    @Override
    public int getId() {
        return chatId;
    }

    @Override
    public String getAttachment() {
        return chatAttachment.toLowerCase();
    }

    @Override
    public int getUnreadCount() {
        return unreadMessageCount;
    }

    @Override
    public String getContentTitle() {
        return senderName;
    }

    @Override
    public String getContentText() {
        return messageText;
    }


    //not needed for chat type
    @Override
    public Alert.Type getType() {
        return null;
    }

    @Override
    public boolean isCurrentSessionNeedToNotify() {
        return isCurrentSessionNeedToNotify;
    }

    @Override
    public boolean isOwnerSession() {
        return chatAttachment.equals(AccountManager.OWNER_SESSION);
    }


    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getChatAttachment() {
        return chatAttachment;
    }

    public void setChatAttachment(String chatAttachment) {
        this.chatAttachment = chatAttachment;
    }

    public Integer getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(Integer unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }

    public Integer getUnreadChatCount() {
        return isCurrentMessagingSession ? unreadChatCount - unreadMessageCount : unreadChatCount;
    }

    public void setUnreadChatCount(Integer unreadChatCount) {
        this.unreadChatCount = unreadChatCount;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Boolean getRead() {
        return isRead;
    }

    public Boolean isCurrentSession() {
        return isCurrentMessagingSession;
    }

    public void setCurrentSession(Boolean currentSession) {
        isRead = currentSession;
        isCurrentMessagingSession = currentSession;
    }

    public void setCurrentSessionNeedToNotify(Boolean currentSessionNeedToNotify) {
        isCurrentSessionNeedToNotify = currentSessionNeedToNotify;
    }
}

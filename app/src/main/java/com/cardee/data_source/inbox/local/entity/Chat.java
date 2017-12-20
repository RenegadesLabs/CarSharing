package com.cardee.data_source.inbox.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "chats")
public class Chat {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Integer id;

    @ColumnInfo(name = "chat_id")
    private Integer chatId;

    @ColumnInfo(name = "attachment")
    private String chatAttachment;

    @ColumnInfo(name = "unread_count")
    private Integer unreadMessageCount;

    @ColumnInfo(name = "name")
    private String recipientName;

    @ColumnInfo(name = "photo_url")
    private String photoUrl;

    @ColumnInfo(name = "car_url")
    private String mCarPhotoUrl;

    @ColumnInfo(name = "last_message")
    private String mLastMessageText;

    @ColumnInfo(name = "last_message_time")
    private String mLastMessageTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCarPhotoUrl() {
        return mCarPhotoUrl;
    }

    public void setCarPhotoUrl(String carPhotoUrl) {
        mCarPhotoUrl = carPhotoUrl;
    }

    public String getLastMessageText() {
        return mLastMessageText;
    }

    public void setLastMessageText(String lastMessageText) {
        mLastMessageText = lastMessageText;
    }

    public String getLastMessageTime() {
        return mLastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        mLastMessageTime = lastMessageTime;
    }

}

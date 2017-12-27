package com.cardee.data_source.inbox.local.chat.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "chat_message",
        foreignKeys = @ForeignKey(
                entity = Chat.class,
                parentColumns = "_id",
                childColumns = "chat_owner_id",
                onDelete = ForeignKey.CASCADE))

public class ChatMessage {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Integer id;

    @ColumnInfo(name = "chat_owner_id")
    private Integer chatId;

    @ColumnInfo(name = "message_id")
    private Integer messageId;

    @ColumnInfo(name = "message")
    private String message;

    @ColumnInfo(name = "is_read") // is read - true, otherwise false
    private Integer isRead;

    @ColumnInfo(name = "is_inbox")
    private Boolean isInbox; // is in box - true, otherwise false

    @ColumnInfo(name = "date")
    private String dateCreated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Boolean getInbox() {
        return isInbox;
    }

    public void setInbox(Boolean inbox) {
        isInbox = inbox;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}

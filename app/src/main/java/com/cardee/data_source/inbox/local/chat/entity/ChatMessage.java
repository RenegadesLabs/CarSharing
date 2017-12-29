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

    @PrimaryKey
    @ColumnInfo(name = "message_id")
    private Integer messageId;

    @ColumnInfo(name = "chat_owner_id")
    private Integer chatId;

    @ColumnInfo(name = "message")
    private String message;

    @ColumnInfo(name = "is_read") // is read - true, otherwise false
    private Boolean isRead;

    @ColumnInfo(name = "is_inbox")
    private Boolean isInbox; // is in box - true, otherwise false

    @ColumnInfo(name = "date")
    private String dateCreated;

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

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage that = (ChatMessage) o;

        return isRead.equals(that.isRead);
    }

    @Override
    public int hashCode() {
        return isRead.hashCode();
    }

    public static class Builder {

        private ChatMessage mChatMessage;

        public Builder() {
            mChatMessage = new ChatMessage();
        }

        public ChatMessage.Builder withChatId(Integer chatId) {
            mChatMessage.chatId = chatId;
            return this;
        }

        public ChatMessage.Builder withMessageId(Integer messageId) {
            mChatMessage.messageId = messageId;
            return this;
        }

        public ChatMessage.Builder withMessage(String message) {
            mChatMessage.message = message;
            return this;
        }

        public ChatMessage.Builder withIsRead(Boolean isRead) {
            mChatMessage.isRead = isRead;
            return this;
        }

        public ChatMessage.Builder withIsInbox(Boolean isInbox) {
            mChatMessage.isInbox = isInbox;
            return this;
        }

        public ChatMessage.Builder withDateCreated(String dateCreated) {
            mChatMessage.dateCreated = dateCreated;
            return this;
        }

        public ChatMessage build() {
            return mChatMessage;
        }
    }
}

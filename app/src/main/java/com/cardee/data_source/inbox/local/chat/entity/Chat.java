package com.cardee.data_source.inbox.local.chat.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "chats")
public class Chat implements Comparable<Chat> {

    public static final String CHAT_SERVER_ID = "chat_id";
    public static final String CHAT_ATTACHMENT = "chat_attachment";
    public static final String CHAT_UNREAD_COUNT = "chat_unread";

    @PrimaryKey
    @ColumnInfo(name = "chat_id")
    private Integer chatId;

    @ColumnInfo(name = "attachment")
    private String chatAttachment;

    @ColumnInfo(name = "is_active")
    private Boolean isActive;

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

    @ColumnInfo(name = "car_title")
    private String mCarTitle;

    @ColumnInfo(name = "license_plate_number")
    private String mCarLicenseNumber;

    @ColumnInfo(name = "time_end")
    private String mBookingTimeEnd;

    @ColumnInfo(name = "time_begin")
    private String mBookingTimeBegin;


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

    public String getCarTitle() {
        return mCarTitle;
    }

    public void setCarTitle(String carTitle) {
        mCarTitle = carTitle;
    }

    public String getCarLicenseNumber() {
        return mCarLicenseNumber;
    }

    public void setCarLicenseNumber(String carLicenseNumber) {
        mCarLicenseNumber = carLicenseNumber;
    }

    public String getBookingTimeEnd() {
        return mBookingTimeEnd;
    }

    public void setBookingTimeEnd(String bookingTimeEnd) {
        mBookingTimeEnd = bookingTimeEnd;
    }

    public String getBookingTimeBegin() {
        return mBookingTimeBegin;
    }

    public void setBookingTimeBegin(String bookingTimeBegin) {
        mBookingTimeBegin = bookingTimeBegin;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public int compareTo(@NonNull Chat chat) {
        return chat.getLastMessageTime().compareTo(mLastMessageTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        if (!chatId.equals(chat.chatId)) return false;
        if (!chatAttachment.equals(chat.chatAttachment)) return false;
        return unreadMessageCount.equals(chat.unreadMessageCount);
    }

    @Override
    public int hashCode() {
        int result = chatId.hashCode();
        result = 31 * result + chatAttachment.hashCode();
        result = 31 * result + unreadMessageCount.hashCode();
        return result;
    }

    public static class Builder {

        private Chat mChat;

        public Builder() {
            mChat = new Chat();
        }

        //base data

        public Chat.Builder withChatId(Integer chatId) {
            mChat.chatId = chatId;
            return this;
        }

        public Chat.Builder withChatAttachment(String chatAttachment) {
            mChat.chatAttachment = chatAttachment;
            return this;
        }

        public Chat.Builder withActive(Boolean isActive) {
            mChat.isActive = isActive;
            return this;
        }


        //inbox chat data
        public Chat.Builder withName(String name) {
            mChat.recipientName = name;
            return this;
        }

        public Chat.Builder withPhotoUrl(String url) {
            mChat.photoUrl = url;
            return this;
        }

        public Chat.Builder withLastMessage(String message) {
            mChat.mLastMessageText = message;
            return this;
        }

        public Chat.Builder withLastMessageTime(String time) {
            mChat.mLastMessageTime = time;
            return this;
        }

        public Chat.Builder withUnreadMessageCount(Integer count) {
            mChat.unreadMessageCount = count;
            return this;
        }

        //car data
        public Chat.Builder withCarTitle(String carTitle) {
            mChat.mCarTitle = carTitle;
            return this;
        }

        public Chat.Builder withCarPhoto(String url) {
            mChat.mCarPhotoUrl = url;
            return this;
        }

        public Chat.Builder withLicenseNumber(String licenseNumber) {
            mChat.mCarLicenseNumber = licenseNumber;
            return this;
        }

        public Chat.Builder withBookingBegin(String beginData) {
            mChat.mBookingTimeBegin = beginData;
            return this;
        }

        public Chat.Builder withBookingEnd(String endData) {
            mChat.mBookingTimeEnd = endData;
            return this;
        }

        public Chat build() {
            return mChat;
        }
    }
}

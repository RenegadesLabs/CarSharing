package com.cardee.data_source.inbox.remote.api.model;

import com.cardee.data_source.inbox.remote.api.model.entity.Booking;
import com.cardee.data_source.inbox.remote.api.model.entity.CarVersion;
import com.cardee.data_source.inbox.remote.api.model.entity.ChatMessage;
import com.cardee.data_source.inbox.remote.api.model.entity.Recipient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chat {

    @Expose
    @SerializedName("chat_id")
    private Integer mChatId;
    @Expose
    @SerializedName("car_version")
    private CarVersion mCarVersion;
    @Expose
    @SerializedName("booking")
    private Booking mBooking;
    @Expose
    @SerializedName("last_message")
    private ChatMessage mLastMessage;
    @Expose
    @SerializedName("recipient")
    private Recipient mRecipient;
    @Expose
    @SerializedName("new_messages")
    private Integer mNewCount;

    public Chat() {
    }

    public Integer getChatId() {
        return mChatId;
    }

    public void setChatId(Integer chatId) {
        mChatId = chatId;
    }

    public CarVersion getCarVersion() {
        return mCarVersion;
    }

    public void setCarVersion(CarVersion carVersion) {
        mCarVersion = carVersion;
    }

    public Booking getBooking() {
        return mBooking;
    }

    public void setBooking(Booking booking) {
        mBooking = booking;
    }

    public ChatMessage getLastMessage() {
        return mLastMessage;
    }

    public void setLastMessage(ChatMessage lastMessage) {
        mLastMessage = lastMessage;
    }

    public Recipient getRecipient() {
        return mRecipient;
    }

    public void setRecipient(Recipient recipient) {
        mRecipient = recipient;
    }

    public Integer getNewCount() {
        return mNewCount;
    }

    public void setNewCount(Integer newCount) {
        mNewCount = newCount;
    }
}

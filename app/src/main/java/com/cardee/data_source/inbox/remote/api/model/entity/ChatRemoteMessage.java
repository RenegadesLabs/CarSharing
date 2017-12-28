package com.cardee.data_source.inbox.remote.api.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatRemoteMessage {

    @Expose
    @SerializedName("message_id")
    private Integer mMessageId;
    @Expose
    @SerializedName("message")
    private String mMessage;
    @Expose
    @SerializedName("is_read")
    private Boolean mIsRead;
    @Expose
    @SerializedName("is_inbox")
    private Boolean mIsInbox;
    @Expose
    @SerializedName("date_created")
    private String mDateCreated;

    public ChatRemoteMessage() {
    }

    public Integer getMessageId() {
        return mMessageId;
    }

    public void setMessageId(Integer messageId) {
        mMessageId = messageId;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getRead() {
        return mIsRead;
    }

    public void setRead(Boolean read) {
        mIsRead = read;
    }

    public Boolean getInbox() {
        return mIsInbox;
    }

    public void setInbox(Boolean inbox) {
        mIsInbox = inbox;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(String dateCreated) {
        mDateCreated = dateCreated;
    }
}

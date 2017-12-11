package com.cardee.data_source.inbox.remote.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewChatMessage {

    @Expose
    @SerializedName("message")
    private String mMessage;

    public NewChatMessage() {
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}

package com.cardee.data_source.inbox.remote.api.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageResponse extends BaseResponse{

    @Expose
    @SerializedName("message")
    private String mMessage;

    public MessageResponse() {
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}

package com.cardee.data_source.inbox.remote.api.response;

import com.cardee.data_source.inbox.remote.api.model.entity.NewMessage;
import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private NewMessage mNewMessage;

    public MessageResponse() {
    }

    public NewMessage getNewMessage() {
        return mNewMessage;
    }

    public void setNewMessage(NewMessage newMessage) {
        mNewMessage = newMessage;
    }
}

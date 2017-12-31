package com.cardee.data_source.inbox.remote.api.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageResponse extends BaseResponse {

    @Expose
    @SerializedName("message_id")
    private Integer messageId;

    public MessageResponse() {
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}

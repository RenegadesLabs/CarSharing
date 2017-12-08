package com.cardee.data_source.remote.api.inbox.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.inbox.model.entity.ChatMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatMessagesResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private ChatMessage[] mMessages;

    public ChatMessagesResponse() {
    }

    public ChatMessage[] getMessages() {
        return mMessages;
    }

    public void setMessages(ChatMessage[] messages) {
        mMessages = messages;
    }
}

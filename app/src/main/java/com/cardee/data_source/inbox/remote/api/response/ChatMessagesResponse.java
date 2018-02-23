package com.cardee.data_source.inbox.remote.api.response;

import com.cardee.data_source.inbox.remote.api.model.entity.ChatRemoteMessage;
import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatMessagesResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private ChatRemoteMessage[] mMessages;

    public ChatMessagesResponse() {
    }

    public ChatRemoteMessage[] getMessages() {
        return mMessages;
    }

    public void setMessages(ChatRemoteMessage[] messages) {
        mMessages = messages;
    }
}

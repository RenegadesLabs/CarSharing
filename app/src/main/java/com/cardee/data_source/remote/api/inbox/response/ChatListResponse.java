package com.cardee.data_source.remote.api.inbox.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.inbox.model.Chat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatListResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private Chat[] mChats;

    public ChatListResponse() {
    }

    public Chat[] getChats() {
        return mChats;
    }

    public void setChats(Chat[] chats) {
        mChats = chats;
    }
}

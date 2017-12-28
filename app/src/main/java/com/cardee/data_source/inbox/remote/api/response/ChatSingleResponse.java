package com.cardee.data_source.inbox.remote.api.response;

import com.cardee.data_source.inbox.remote.api.model.ChatRemote;
import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatSingleResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private ChatRemote mChatRemote;

    public ChatSingleResponse() {
    }

    public ChatRemote getChatRemote() {
        return mChatRemote;
    }

    public void setChatRemote(ChatRemote chatRemote) {
        mChatRemote = chatRemote;
    }


}

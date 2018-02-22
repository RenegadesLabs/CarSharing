package com.cardee.data_source.inbox.remote.api.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.inbox.remote.api.model.ChatRemote;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatListResponse extends BaseResponse {

    @Expose
    @SerializedName("dataList")
    private ChatRemote[] mChatRemotes;

    public ChatListResponse() {
    }

    public ChatRemote[] getChatRemotes() {
        return mChatRemotes;
    }

    public void setChatRemotes(ChatRemote[] chatRemotes) {
        mChatRemotes = chatRemotes;
    }
}

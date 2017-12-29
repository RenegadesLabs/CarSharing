package com.cardee.data_source.inbox.remote.api.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private String mReadStatus;

    public MessageResponse() {
    }

    public String getReadStatus() {
        return mReadStatus;
    }

    public void setReadStatus(String readStatus) {
        mReadStatus = readStatus;
    }
}

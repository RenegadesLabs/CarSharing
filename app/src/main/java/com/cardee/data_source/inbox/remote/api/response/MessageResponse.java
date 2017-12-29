package com.cardee.data_source.inbox.remote.api.response;

import com.cardee.data_source.inbox.remote.api.model.entity.ReadStatus;
import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private ReadStatus mReadStatus;

    public MessageResponse() {
    }

    public ReadStatus getReadStatus() {
        return mReadStatus;
    }

    public void setReadStatus(ReadStatus readStatus) {
        mReadStatus = readStatus;
    }
}

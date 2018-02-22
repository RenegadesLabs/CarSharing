package com.cardee.data_source.inbox.remote.api.response;

import com.cardee.data_source.inbox.remote.api.model.AlertRemote;
import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlertListResponse extends BaseResponse {

    @Expose
    @SerializedName("dataList")
    private List<AlertRemote> mRemoteList;

    public AlertListResponse() {
    }

    public List<AlertRemote> getRemoteList() {
        return mRemoteList;
    }

    public void setRemoteList(List<AlertRemote> remoteList) {
        mRemoteList = remoteList;
    }
}

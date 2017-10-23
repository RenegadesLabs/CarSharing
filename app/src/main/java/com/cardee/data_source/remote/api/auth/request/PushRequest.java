package com.cardee.data_source.remote.api.auth.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushRequest {

    @Expose
    @SerializedName("device_token")
    private String deviceToken;

    public PushRequest(){

    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}

package com.cardee.data_source.remote.api.auth.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushRequest {

    @Expose
    @SerializedName("device_token")
    private String deviceToken;
    @Expose
    @SerializedName("is_fcm")
    private Boolean isFcm = true;

    public PushRequest() {

    }

    public Boolean getFcm() {
        return isFcm;
    }

    public void setFcm(Boolean fcm) {
        isFcm = fcm;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}

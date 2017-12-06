package com.cardee.data_source.remote.api.profile.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePhoneRequest {

    @Expose
    @SerializedName("phone")
    private String phone;

    public ChangePhoneRequest() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

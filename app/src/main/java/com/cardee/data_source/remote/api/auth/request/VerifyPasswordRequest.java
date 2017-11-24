package com.cardee.data_source.remote.api.auth.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyPasswordRequest {

    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("psd_confirm")
    private String passwordConfirm;

    public VerifyPasswordRequest() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}

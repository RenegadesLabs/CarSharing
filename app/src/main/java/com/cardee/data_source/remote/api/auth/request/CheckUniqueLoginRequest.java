package com.cardee.data_source.remote.api.auth.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckUniqueLoginRequest {

    @Expose
    @SerializedName("login")
    private String mLogin;

    @Expose
    @SerializedName("password")
    private String mPassword;

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        this.mLogin = login;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }
}

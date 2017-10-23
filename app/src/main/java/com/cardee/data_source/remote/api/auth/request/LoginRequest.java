package com.cardee.data_source.remote.api.auth.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @Expose
    @SerializedName("login")
    private String login;
    @Expose
    @SerializedName("password")
    private String password;

    public LoginRequest(){

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.cardee.data_source.remote.api.auth.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

public class SignUpRequest {

    @Expose
    @SerializedName("login")
    private String login;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("pwd_confirm")
    private String passwordConfirm;
    @Expose
    @SerializedName("name")
    private String name;

//
//    @Expose
//    @SerializedName("picture")
//    private String picture;

    public SignUpRequest() {

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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
//
//    public String getPicture() {
//        return picture;
//    }
//
//    public void setPicture(String picture) {
//        this.picture = picture;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

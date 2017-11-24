package com.cardee.data_source.remote.api.auth.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPassRequest {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Expose
    @SerializedName("email")
    private String email;
}

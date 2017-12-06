package com.cardee.data_source.remote.api.profile.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeEmailRequest {

    @Expose
    @SerializedName("email")
    private String email;

    public ChangeEmailRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

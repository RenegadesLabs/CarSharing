package com.cardee.data_source.remote.api.profile.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassChangeRequest {

    @Expose
    @SerializedName("old_password")
    private String oldPass;

    @Expose
    @SerializedName("new_password")
    private String newPass;

    public PassChangeRequest() {
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}

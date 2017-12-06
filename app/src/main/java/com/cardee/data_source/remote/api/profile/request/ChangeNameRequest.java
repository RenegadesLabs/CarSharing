package com.cardee.data_source.remote.api.profile.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeNameRequest {

    @Expose
    @SerializedName("name")
    private String name;

    public ChangeNameRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

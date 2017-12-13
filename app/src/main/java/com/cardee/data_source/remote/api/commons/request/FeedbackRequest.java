package com.cardee.data_source.remote.api.commons.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackRequest {

    @Expose
    @SerializedName("message")
    private String message;

    public FeedbackRequest() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

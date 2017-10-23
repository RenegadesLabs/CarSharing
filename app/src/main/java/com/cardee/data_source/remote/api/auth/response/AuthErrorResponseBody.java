package com.cardee.data_source.remote.api.auth.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;


public class AuthErrorResponseBody {

    @Expose
    @SerializedName("errors")
    private Map<String, String[]> errors;

    public AuthErrorResponseBody() {

    }

    public Map<String, String[]> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String[]> errors) {
        this.errors = errors;
    }

}

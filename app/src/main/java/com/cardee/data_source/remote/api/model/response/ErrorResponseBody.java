package com.cardee.data_source.remote.api.model.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ErrorResponseBody {
    @Expose
    @SerializedName("errors")
    private Map<String, String[]> errors;

    public ErrorResponseBody(){

    }

    public Map<String, String[]> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String[]> errors) {
        this.errors = errors;
    }
}

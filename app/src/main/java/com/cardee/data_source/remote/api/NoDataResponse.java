package com.cardee.data_source.remote.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoDataResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private ErrorResponseBody errors;

    public NoDataResponse() {

    }

    public ErrorResponseBody getErrors() {
        return errors;
    }

    public void setErrors(ErrorResponseBody errors) {
        this.errors = errors;
    }
}

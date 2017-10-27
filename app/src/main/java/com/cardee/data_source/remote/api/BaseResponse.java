package com.cardee.data_source.remote.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    public static final int CODE_SUCCESS = 200;

    public static final int ERROR_CODE_UNAUTHORIZED = 422;
    public static final int ERROR_CODE_INTERNAL_SERVER_ERROR = 500;

    @Expose
    @SerializedName("code")
    private Integer code;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("success")
    private Boolean success;

    public BaseResponse() {

    }

    public Integer getResponseCode() {
        return code;
    }

    public void setResponseCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public boolean isSuccessful() {
        return success != null && success;
    }
}

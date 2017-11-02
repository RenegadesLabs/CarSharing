package com.cardee.data_source.remote.api.auth.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;


public class AuthErrorResponseBody {

    public static final String ERROR_FIELD_AUTHENTICATION = "authentication";
    public static final String ERROR_FIELD_LOGIN = "login";
    public static final String ERROR_FIELD_PASSWORD = "password";
    public static final String ERROR_FIELD_NAME = "name";


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

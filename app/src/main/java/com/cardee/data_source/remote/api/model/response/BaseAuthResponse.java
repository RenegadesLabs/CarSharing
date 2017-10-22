package com.cardee.data_source.remote.api.model.response;

import com.cardee.data_source.remote.api.model.adapter.JsonToAuthResponseAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

public class BaseAuthResponse extends BaseResponse {

    public static final String ERROR_AUTHENTICATION = "authentication";
    public static final String ERROR_LOGIN = "login";
    public static final String ERROR_PASSWORD = "password";
    public static final String ERROR_NAME = "name";

    @Expose
    @SerializedName("data")
    @JsonAdapter(JsonToAuthResponseAdapter.class)
    private BaseAuthResponseBody body;

    public static class BaseAuthResponseBody extends ErrorResponseBody {
        private String token;
    }

    public BaseAuthResponseBody getBody() {
        return body;
    }

    public void setBody(BaseAuthResponseBody body) {
        this.body = body;
    }
}
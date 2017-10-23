package com.cardee.data_source.remote.api.auth.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.ErrorResponseBody;
import com.cardee.data_source.remote.api.auth.adapter.JsonToAuthResponseAdapter;
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

    public static class BaseAuthResponseBody extends AuthErrorResponseBody {
        private String token;

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

    public BaseAuthResponseBody getBody() {
        return body;
    }

    public void setBody(BaseAuthResponseBody body) {
        this.body = body;
    }
}

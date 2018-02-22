package com.cardee.data_source.remote.api.auth.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.auth.adapter.JsonToAuthResponseAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

public class BaseAuthResponse extends BaseResponse {

    @Expose
    @SerializedName("dataList")
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

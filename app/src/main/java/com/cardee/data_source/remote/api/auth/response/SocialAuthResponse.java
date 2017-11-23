package com.cardee.data_source.remote.api.auth.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialAuthResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private Data body;

    public class Data {
        @Expose
        @SerializedName("access_token")
        String token;

        @Expose
        @SerializedName("signup_required")
        Boolean signupRequired;

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token.split(" ")[1];
        }

        public void setSignupRequired(boolean isSignupRequired) {
            this.signupRequired = isSignupRequired;
        }

        public boolean isSignupRequired() {
            return signupRequired;
        }
    }

    public Data getBody() {
        return body;
    }
}

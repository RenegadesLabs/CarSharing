package com.cardee.data_source.remote.api.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialLoginRequest {

    public enum Provider {
        FACEBOOK, GOOGLE
    }

    @Expose
    @SerializedName("social_provider")
    private Provider provider;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("expired_in")
    private String expired;

    public SocialLoginRequest() {

    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }
}

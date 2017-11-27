package com.cardee.data_source.remote.api.auth.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialLoginRequest {

    public final static String FACEBOOK = "FACEBOOK";
    public final static String GOOGLE = "GOOGLE";

    public enum Provider {
        FACEBOOK, GOOGLE
    }

    @Expose
    @SerializedName("social_provider")
    private String provider;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("expired_in")
    private String expired;

    public SocialLoginRequest() {

    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
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

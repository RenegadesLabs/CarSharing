package com.cardee.data_source.remote.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import okhttp3.Request;
import okhttp3.Response;

public class AccountManager {

    private static final String AUTH_TOKEN_STORE = "_auth_token_store";
    private static final String AUTH_TOKEN = "_auth_token";
    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String DEFAULT_AUTH_TOKEN = "";

    private static final String ATTACHMENT = "attachment";
    private static final String OWNER_SESSION = "owner";
    private static final String RENTER_SESSION = "renter";
    private static final String FCM_TOKEN_AUTH = "fcm_token_auth";

    private static AccountManager INSTANCE;

    public static AccountManager getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AccountManager(context);
        }
        return INSTANCE;
    }

    private SharedPreferences mPrefs;

    private AccountManager(Context context) {
        mPrefs = context.getSharedPreferences(AUTH_TOKEN_STORE, Context.MODE_PRIVATE);
    }

    public Request modifyRequestHeaders(Request request) {
        Request.Builder builder = request.newBuilder();
        builder.addHeader(AUTH_HEADER_NAME, mPrefs.getString(AUTH_TOKEN, DEFAULT_AUTH_TOKEN));
        return builder.build();
    }

    public void handleResponseHeaders(Response response) {
        String headerValue = response.header(AUTH_HEADER_NAME);
        if (headerValue != null) {
            saveToken(headerValue);
        }
    }

    public void saveToken(String token) {
        mPrefs.edit()
                .putString(ATTACHMENT, OWNER_SESSION)
                .putString(AUTH_TOKEN, token)
                .apply();
    }

    public void saveFcmAuthAction() {
        mPrefs.edit()
                .putBoolean(FCM_TOKEN_AUTH, true)
                .apply();
    }

    //TODO: delete after user logged state handling implemented
    public boolean isLogedIn() {
        String string = mPrefs.getString(AUTH_TOKEN, null);
        return string != null;
    }

    public void onLogout() {
        mPrefs.edit().remove(AUTH_TOKEN).apply();
    }

    public boolean isLoggedIn() {
        return !mPrefs.getString(AUTH_TOKEN, "").equals("");
    }

    public boolean isFcmTokenAuthenticated() {
        return mPrefs.getBoolean(FCM_TOKEN_AUTH, false);
    }

    public String getSessionInfo() {
        return mPrefs.getString(ATTACHMENT, OWNER_SESSION);
    }
}

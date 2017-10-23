package com.cardee.data_source.util;


import android.content.Context;
import android.content.SharedPreferences;

public class AuthHelper {
    private static final String TOKEN_STORAGE = "cardee_auth_token_store";

    private static final String TOKEN_KEY = "cardee_auth_token";

    private SharedPreferences mPrefs;

    private AuthHelper(Context context) {
        mPrefs = context.getSharedPreferences(TOKEN_STORAGE, Context.MODE_PRIVATE);
    }

    public static AuthHelper from(Context context) {
        return new AuthHelper(context);
    }

    public void saveToken(String token) {
        mPrefs.edit().putString(TOKEN_KEY, token).apply();
    }

    public String readToken() {
        return mPrefs.getString(TOKEN_KEY, "");
    }

    public void recycle() {
        mPrefs = null;
    }
}


package com.cardee.data_source.remote.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.cardee.data_source.inbox.remote.api.model.entity.NotificationData;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Request;
import okhttp3.Response;

public class AccountManager {

    private static AccountManager INSTANCE;

    private static final String AUTH_TOKEN_STORE = "_auth_token_store";
    private static final String AUTH_TOKEN = "_auth_token";
    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String DEFAULT_AUTH_TOKEN = "";
    private static final String FCM_TOKEN_AUTH = "fcm_token_auth";

    public static final String OWNER_SESSION = "owner";
    public static final String RENTER_SESSION = "renter";

    private static final String SESSION = "attachment";
    private static final String RENTER_CHAT_NOTIFICATIONS = "renter_chat_notify";
    private static final String OWNER_CHAT_NOTIFICATIONS = "owner_chat_notify";
    private static final String RENTER_ALERT_NOTIFICATIONS = "renter_alert_notify";
    private static final String OWNER_ALERT_NOTIFICATIONS = "owner_alert_notify";

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
                .putString(SESSION, OWNER_SESSION)
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

    public void saveNotificationData(NotificationData data) {
        Completable.fromRunnable(() -> mPrefs.edit()
                .putInt(OWNER_CHAT_NOTIFICATIONS, data.getOwnerChatMessages())
                .putInt(OWNER_ALERT_NOTIFICATIONS, data.getOwnerAlertMessages())
                .putInt(RENTER_CHAT_NOTIFICATIONS, data.getOwnerChatMessages())
                .putInt(RENTER_ALERT_NOTIFICATIONS, data.getOwnerAlertMessages())
                .apply())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public NotificationData getNotificationData() {
        NotificationData data = new NotificationData(getSessionInfo());
        data.setOwnerAlertMessages(mPrefs.getInt(OWNER_ALERT_NOTIFICATIONS, 0));
        data.setOwnerChatMessages(mPrefs.getInt(OWNER_CHAT_NOTIFICATIONS, 0));
        data.setRenterAlertMessages(mPrefs.getInt(RENTER_ALERT_NOTIFICATIONS, 0));
        data.setRenterChatMessages(mPrefs.getInt(RENTER_CHAT_NOTIFICATIONS, 0));
        return data;
    }

    public void onLogout() {
        mPrefs.edit().remove(AUTH_TOKEN).apply();

        // Logout from Facebook
        LoginManager.getInstance().logOut();
    }

    public boolean isLoggedIn() {
        return isLoggedInViaFacebook() || !mPrefs.getString(AUTH_TOKEN, "").equals("");
    }

    private boolean isLoggedInViaFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public boolean isFcmTokenAuthenticated() {
        return mPrefs.getBoolean(FCM_TOKEN_AUTH, false);
    }

    public String getSessionInfo() {
        return mPrefs.getString(SESSION, "");
    }

    public void setSession(String session) {
        mPrefs.edit()
                .putString(SESSION, session)
                .apply();
    }
}

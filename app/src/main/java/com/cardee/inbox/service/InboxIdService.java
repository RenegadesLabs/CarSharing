package com.cardee.inbox.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class InboxIdService extends FirebaseInstanceIdService {

    private static final String TAG = InboxIdService.class.getName();

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);

        pushTokenToServer(refreshedToken);
    }

    private void pushTokenToServer(String refreshedToken) {

    }
}

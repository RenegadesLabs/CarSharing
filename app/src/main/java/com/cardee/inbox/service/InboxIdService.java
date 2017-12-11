package com.cardee.inbox.service;

import android.util.Log;
import android.widget.Toast;

import com.cardee.CardeeApp;
import com.cardee.data_source.remote.api.auth.request.PushRequest;
import com.cardee.data_source.inbox.remote.api.InboxApi;
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
        PushRequest pushRequest = new PushRequest();
        pushRequest.setDeviceToken(refreshedToken);
        InboxApi inboxApi = CardeeApp.retrofit.create(InboxApi.class);
        inboxApi.pushToken(pushRequest)
                .retry(5000)
                .subscribe(baseAuthResponse -> Toast.makeText(CardeeApp.context, "Token is delivered!", Toast.LENGTH_SHORT).show(),
                        throwable -> Toast.makeText(CardeeApp.context, "Send request failure!", Toast.LENGTH_SHORT).show());

    }
}

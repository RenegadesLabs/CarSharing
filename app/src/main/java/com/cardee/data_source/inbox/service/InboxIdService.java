package com.cardee.data_source.inbox.service;

import android.util.Log;
import android.widget.Toast;

import com.cardee.CardeeApp;
import com.cardee.data_source.remote.api.auth.Authentication;
import com.cardee.data_source.remote.api.auth.request.PushRequest;
import com.cardee.data_source.remote.service.AccountManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        Authentication authApi = CardeeApp.retrofit.create(Authentication.class);
        authApi.pushToken(pushRequest)
                .retry(5)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    AccountManager.getInstance(CardeeApp.context).saveFcmAuthAction();
                    Toast.makeText(CardeeApp.context, "Token is delivered!", Toast.LENGTH_SHORT).show();
                }, throwable -> Log.e(TAG, throwable.getMessage()));
    }
}

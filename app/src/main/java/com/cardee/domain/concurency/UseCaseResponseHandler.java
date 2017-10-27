package com.cardee.domain.concurency;

import android.os.Handler;
import android.os.Looper;

import com.cardee.domain.UseCase;

public final class UseCaseResponseHandler {

    private final Handler mHandler;

    public UseCaseResponseHandler() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    <R extends UseCase.ResponseValues> void notifyResponse(final R response, final UseCase.Callback<R> callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response);
            }
        });
    }

    <R extends UseCase.ResponseValues> void onError(final R message, final UseCase.Callback<R> callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(message);
            }
        });
    }
}

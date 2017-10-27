package com.cardee.domain.concurency;

import android.support.annotation.NonNull;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;

public class UIThreadCallback<V extends UseCase.ResponseValues> implements UseCase.Callback<V> {

    private final UseCaseResponseHandler mResponseHandler;
    private final UseCase.Callback<V> mCallback;

    public UIThreadCallback(@NonNull UseCaseResponseHandler responseHandler, @NonNull UseCase.Callback<V> callback) {
        mResponseHandler = responseHandler;
        mCallback = callback;
    }

    @Override
    public void onSuccess(V response) {
        mResponseHandler.notifyResponse(response, mCallback);
    }

    @Override
    public void onError(Error error) {
        mResponseHandler.onError(error, mCallback);
    }
}

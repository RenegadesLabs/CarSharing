package com.cardee.data_source.remote;

import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.CommonsDataSource;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerProfileDataSource;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.commons.Commons;
import com.cardee.data_source.remote.api.commons.request.FeedbackRequest;

import io.reactivex.functions.Consumer;

public class RemoteCommonsDataSource implements CommonsDataSource {

    private static final String TAG = RemoteCommonsDataSource.class.getSimpleName();
    private static RemoteCommonsDataSource instance;
    private final Commons mApi;

    private RemoteCommonsDataSource() {
        mApi = CardeeApp.retrofit.create(Commons.class);
    }

    public static RemoteCommonsDataSource getInstance() {
        if (instance == null) {
            instance = new RemoteCommonsDataSource();
        }
        return instance;
    }

    @Override
    public void sendFeedback(String message, final OwnerProfileDataSource.NoResponseCallback callback) {
        FeedbackRequest request = new FeedbackRequest();
        request.setMessage(message);
        mApi.sendFeedback(request).subscribe(new Consumer<NoDataResponse>() {
            @Override
            public void accept(NoDataResponse noDataResponse) throws Exception {
                if (noDataResponse.isSuccessful()) {
                    callback.onSuccess();
                    return;
                }
                handleErrorResponse(callback, noDataResponse);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, throwable.getMessage());
                callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
            }
        });
    }

    private void handleErrorResponse(OwnerProfileDataSource.BaseCallback callback, BaseResponse response) {
        if (response.getResponseCode() == BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR) {
            callback.onError(new Error(Error.Type.SERVER, "Server error"));
        } else if (response.getResponseCode() == BaseResponse.ERROR_CODE_UNAUTHORIZED) {
            callback.onError(new Error(Error.Type.AUTHORIZATION, "Unauthorized"));
        } else {
            callback.onError(new Error(Error.Type.OTHER, "Undefined error"));
            throw new IllegalArgumentException("Unsupported response code: " + response.getResponseCode());
        }
    }
}

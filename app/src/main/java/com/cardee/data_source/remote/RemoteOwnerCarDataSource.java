package com.cardee.data_source.remote;

import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarDataSource;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.profile.Profile;
import com.cardee.data_source.remote.api.profile.response.CarsResponse;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

public class RemoteOwnerCarDataSource implements OwnerCarDataSource {

    private static final String TAG = RemoteOwnerCarDataSource.class.getSimpleName();

    private static RemoteOwnerCarDataSource INSTANCE;

    private Profile mApi;

    private RemoteOwnerCarDataSource() {
        mApi = CardeeApp.retrofit.create(Profile.class);
    }

    public static RemoteOwnerCarDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteOwnerCarDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainCars(final Callback callback) {
        mApi.loadOwnersCarList().subscribe(new Consumer<CarsResponse>() {
            @Override
            public void accept(CarsResponse carsResponse) throws Exception {
                if (carsResponse.isSuccessful()) {
                    callback.onSuccess(carsResponse.getCars());
                    return;
                }
                handleErrorResponse(callback, carsResponse);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, throwable.getMessage());
                callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
            }
        });
    }

    private void handleErrorResponse(Callback callback, BaseResponse response) {
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

package com.cardee.data_source.remote;

import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarDataSource;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.profile.Profile;
import com.cardee.data_source.remote.api.profile.response.CarsResponse;

import java.io.IOException;

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
    public void obtainCars(Callback callback) {
        try {
            Response<CarsResponse> response = mApi.loadOwnersCarList().execute();
            if (response.isSuccessful()) {
                callback.onSuccess(response.body().getCars());
                return;
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
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

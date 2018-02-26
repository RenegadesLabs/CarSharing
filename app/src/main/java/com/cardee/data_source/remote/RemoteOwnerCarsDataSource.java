package com.cardee.data_source.remote;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarsDataSource;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.profile.Profile;
import com.cardee.data_source.remote.api.profile.response.CarsResponse;

import java.io.IOException;

import retrofit2.Response;

public class RemoteOwnerCarsDataSource implements OwnerCarsDataSource {

    private static final String TAG = RemoteOwnerCarsDataSource.class.getSimpleName();

    private static RemoteOwnerCarsDataSource INSTANCE;

    private final Profile mApi;

    private RemoteOwnerCarsDataSource() {
        mApi = CardeeApp.retrofit.create(Profile.class);
    }

    public static RemoteOwnerCarsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteOwnerCarsDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainCars(final Callback callback) {
        try {
            Response<CarsResponse> response = mApi.loadOwnersCarList().execute();
            if (response.isSuccessful() && response.body() != null) {
                callback.onSuccess(response.body().getCars());
                return;
            }
            handleErrorResponse(callback, response.body());
        } catch (IOException e) {
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    private void handleErrorResponse(Callback callback, BaseResponse response) {
        if (response == null) {
            callback.onError(new Error(Error.Type.OTHER, "null response"));
        } else if (response.getResponseCode() == BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR) {
            callback.onError(new Error(Error.Type.SERVER, "Server error"));
        } else if (response.getResponseCode() == BaseResponse.ERROR_CODE_UNAUTHORIZED) {
            callback.onError(new Error(Error.Type.AUTHORIZATION, "Unauthorized"));
        } else {
            callback.onError(new Error(Error.Type.OTHER, "Undefined error"));
//            throw new IllegalArgumentException("Unsupported response code: " + response.getResponseCode());
        }
    }
}

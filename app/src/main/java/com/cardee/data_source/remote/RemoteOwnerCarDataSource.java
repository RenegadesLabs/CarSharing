package com.cardee.data_source.remote;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarDataSource;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.cars.Cars;
import com.cardee.data_source.remote.api.cars.response.CarResponse;

import java.io.IOException;

import retrofit2.Response;


public class RemoteOwnerCarDataSource implements OwnerCarDataSource {

    private static RemoteOwnerCarDataSource INSTANCE;

    private final Cars mApi;

    private RemoteOwnerCarDataSource() {
        mApi = CardeeApp.retrofit.create(Cars.class);
    }

    public static RemoteOwnerCarDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteOwnerCarDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainCar(int id, Callback callback) {
        try {
            Response<CarResponse> response = mApi.getCar(id).execute();
            if (response.isSuccessful() && response.body() != null) {
                callback.onSuccess(response.body().getCarBody());
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            callback.onError(new Error(Error.Type.LOST_CONNECTION, "Internet connection lost"));
        }
    }

    private void handleErrorResponse(BaseResponse response, OwnerCarDataSource.Callback callback) {
        if (response == null) {
            callback.onError(new Error(Error.Type.OTHER, "null"));
            return;
        }
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

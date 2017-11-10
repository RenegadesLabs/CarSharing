package com.cardee.data_source.remote;


import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.NewCarDataSource;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.cars.Cars;
import com.cardee.data_source.remote.api.cars.request.NewCarData;
import com.cardee.data_source.remote.api.cars.response.CreateCarResponse;
import com.cardee.data_source.remote.validator.NewCarValidator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class RemoteNewCarDataSource implements NewCarDataSource {

    private static final String TAG = RemoteNewCarDataSource.class.getSimpleName();

    private static RemoteNewCarDataSource INSTANCE;
    private final Cars api;

    private RemoteNewCarDataSource() {
        api = CardeeApp.retrofit.create(Cars.class);
    }

    public static RemoteNewCarDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteNewCarDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainSavedCarData(CacheCallback callback) {
        //No implementation need in remote data source
    }

    @Override
    public void saveCarData(NewCarData carData, boolean forcePush, Callback callback) {
        if (forcePush) {
            NewCarValidator validator = new NewCarValidator();
            if (validator.isValid(carData)) {
                Call<CreateCarResponse> request = api.createCar(carData);
                try {
                    Response<CreateCarResponse> response = request.execute();
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body().getResponseBody().getCarId());
                        return;
                    }
                    handleErrorResponse(response.body(), callback);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                    callback.onError(new Error(Error.Type.LOST_CONNECTION, "Internet connection lost"));
                }
            } else {
                callback.onError(new Error(Error.Type.INVALID_REQUEST, "Request body is not valid"));
            }

        }
    }

    private void handleErrorResponse(BaseResponse response, Callback callback) {
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

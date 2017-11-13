package com.cardee.data_source.remote;

import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.cars.Cars;
import com.cardee.data_source.remote.api.cars.request.NewCarData;

import java.io.IOException;

import retrofit2.Response;


public class RemoteCarEditDataSource implements CarEditDataSource {

    private static final String TAG = RemoteCarEditDataSource.class.getSimpleName();
    private static RemoteCarEditDataSource INSTANCE;

    private final Cars api;

    private RemoteCarEditDataSource() {
        api = CardeeApp.retrofit.create(Cars.class);
    }

    public static RemoteCarEditDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteCarEditDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void updateLocation(Integer id, NewCarData carData, CarEditDataSource.Callback callback) {
        try {
            Response<BaseResponse> response = api.updateLocation(id, carData).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void updateInfo(Integer id, NewCarData carData, CarEditDataSource.Callback callback) {
        try {
            Response<BaseResponse> response = api.updateInfo(id, carData).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    private void handleErrorResponse(BaseResponse response, CarEditDataSource.Callback callback) {
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

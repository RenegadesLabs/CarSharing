package com.cardee.data_source.remote;


import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.AvailabilityDataSource;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.ErrorResponseBody;
import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.cars.Availability;
import com.cardee.data_source.remote.api.cars.request.DailyAvailability;
import com.cardee.data_source.remote.api.cars.request.HourlyAvailability;

import java.io.IOException;
import java.util.Map;

import retrofit2.Response;

public class RemoteAvailabilityDataSource implements AvailabilityDataSource {

    private static final String TAG = RemoteAvailabilityDataSource.class.getSimpleName();
    private static RemoteAvailabilityDataSource INSTANCE;

    private final Availability api;

    private RemoteAvailabilityDataSource() {
        api = CardeeApp.retrofit.create(Availability.class);
    }

    public static RemoteAvailabilityDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteAvailabilityDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void saveDailyAvailability(int id, String[] dates, Callback callback) {
        DailyAvailability request = new DailyAvailability();
        request.setDates(dates == null ? new String[]{} : dates);
        try {
            Response<NoDataResponse> response = api.saveDailyAvailability(id, request).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, "Cannot connect to the server"));
        }
    }

    @Override
    public void saveHourlyAvailability(int id, String[] dates, String startTime, String endTime, Callback callback) {
        HourlyAvailability request = new HourlyAvailability();
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setDates(dates == null ? new String[]{} : dates);
        try {
            Response<NoDataResponse> response = api.saveHourlyAvailability(id, request).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, "Cannot connect to the server"));
        }
    }

    @Override
    public void saveDailyTiming(int id, String pickupTime, String returnTime, Callback callback) {

    }

    private void handleErrorResponse(NoDataResponse response, Callback callback) {
        if (response == null) {
            callback.onError(new Error(Error.Type.OTHER, "null"));
            return;
        }
        Map<String, String> errorMap = response.getErrors().getErrors();
        if (response.getResponseCode() == BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR) {
            callback.onError(new Error(Error.Type.SERVER, errorMap.get(ErrorResponseBody.ERROR_FIELD_DESCRIPTION)));
        } else if (response.getResponseCode() == BaseResponse.ERROR_CODE_UNAUTHORIZED) {
            callback.onError(new Error(Error.Type.AUTHORIZATION, errorMap.get(ErrorResponseBody.ERROR_FIELD_DESCRIPTION)));
        } else {
            callback.onError(new Error(Error.Type.OTHER, "Undefined error"));
            throw new IllegalArgumentException("Unsupported response code: " + response.getResponseCode());
        }
    }
}

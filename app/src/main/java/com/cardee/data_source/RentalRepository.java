package com.cardee.data_source;


import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.ErrorResponseBody;
import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.cars.Rental;
import com.cardee.data_source.remote.api.cars.request.DailyRentalDetails;

import java.io.IOException;
import java.util.Map;

import retrofit2.Response;

public class RentalRepository implements RentalDataSource {

    private static final String TAG = RentalRepository.class.getSimpleName();

    private static RentalRepository INSTANCE;
    private Rental api;

    private RentalRepository() {
        api = CardeeApp.retrofit.create(Rental.class);
    }

    public static RentalRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RentalRepository();
        }
        return INSTANCE;
    }

    @Override
    public void saveDailyRentalDetail(int id, DailyRentalDetails rentalDetails, Callback callback) {
        try {
            Response<NoDataResponse> response = api.saveDailyRental(id, rentalDetails).execute();
            if (response.isSuccessful()) {
                OwnerCarRepository.getInstance().refresh(id);
                OwnerCarsRepository.getInstance().refreshCars();
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, "Cannot connect to the server"));
        }
    }

    private void handleErrorResponse(NoDataResponse response, RentalDataSource.Callback callback) {
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

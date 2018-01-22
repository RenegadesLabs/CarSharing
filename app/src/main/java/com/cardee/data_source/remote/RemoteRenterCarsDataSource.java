package com.cardee.data_source.remote;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.RenterCarsDataSource;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.offers.Offers;
import com.cardee.data_source.remote.api.offers.response.OffersResponse;

import java.io.IOException;

import retrofit2.Response;


public class RemoteRenterCarsDataSource implements RenterCarsDataSource {


    private static RemoteRenterCarsDataSource INSTANCE;

    private Offers mApi;

    public RemoteRenterCarsDataSource() {
        mApi = CardeeApp.retrofit.create(Offers.class);
    }

    public static RemoteRenterCarsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteRenterCarsDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainCars(OffersCallback offersCallback) {
        try {
            Response<OffersResponse> response = mApi.browseCars().execute();
            if (response.isSuccessful()) {
                offersCallback.onSuccess(response.body().getOfferResponseBody());
                return;
            }
            handleErrorResponse(offersCallback, response.body());
        } catch (IOException e) {
            e.printStackTrace();
            offersCallback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void addCarToFavorites(int carId, Callback callback) {
        mApi.addCarToFavorites(carId).subscribe(noDataResponse -> {
            if (noDataResponse.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(callback, noDataResponse);
        }, throwable ->
            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage())));
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

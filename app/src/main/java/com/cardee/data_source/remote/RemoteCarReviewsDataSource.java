package com.cardee.data_source.remote;

import com.cardee.CardeeApp;
import com.cardee.data_source.CarReviewsDataSource;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.reviews.Reviews;
import com.cardee.data_source.remote.api.reviews.response.CarReviewsResponse;

import java.io.IOException;

import retrofit2.Response;

public class RemoteCarReviewsDataSource implements CarReviewsDataSource {

    private static RemoteCarReviewsDataSource instance;
    private final Reviews mApi;

    private RemoteCarReviewsDataSource() {
        mApi = CardeeApp.retrofit.create(Reviews.class);
    }

    public static RemoteCarReviewsDataSource getInstance() {
        if (instance == null) {
            instance = new RemoteCarReviewsDataSource();
        }
        return instance;
    }

    @Override
    public void loadCarReviews(int id, Callback callback) {
        try {
            Response<CarReviewsResponse> response = mApi.getCarReviews(id).execute();
            if (response.isSuccessful() && response.body() != null && response.body().getCarReviews() != null) {
                callback.onSuccess(response.body().getCarReviews());
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            callback.onError(new Error(Error.Type.LOST_CONNECTION, "Internet connection lost"));
        }
    }

    private void handleErrorResponse(BaseResponse response, CarReviewsDataSource.Callback callback) {
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

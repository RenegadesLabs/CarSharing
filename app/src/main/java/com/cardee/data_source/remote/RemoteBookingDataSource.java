package com.cardee.data_source.remote;


import com.cardee.CardeeApp;
import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.booking.Bookings;
import com.cardee.data_source.remote.api.booking.response.BookingResponse;

import java.io.IOException;

import retrofit2.Response;

public class RemoteBookingDataSource implements BookingDataSource {

    private static RemoteBookingDataSource INSTANCE;

    private final Bookings api;

    private RemoteBookingDataSource() {
        api = CardeeApp.retrofit.create(Bookings.class);
    }

    public static RemoteBookingDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteBookingDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainOwnerBookings(Callback callback) {
        try {
            Response<BookingResponse> response = api.getOwnerBookings().execute();
            if (response.isSuccessful() && response.body() != null) {
                callback.onSuccess(response.body().getBookings());
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException ex) {
            callback.onError(new Error(Error.Type.LOST_CONNECTION, "Internet connection lost"));
        }
    }

    @Override
    public void obtainRenterBookings(Callback callback) {

    }

    private void handleErrorResponse(BookingResponse response, BookingDataSource.Callback callback) {
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

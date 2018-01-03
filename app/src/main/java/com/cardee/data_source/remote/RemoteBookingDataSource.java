package com.cardee.data_source.remote;


import android.util.Log;
import android.util.LruCache;

import com.cardee.CardeeApp;
import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.booking.Bookings;
import com.cardee.data_source.remote.api.booking.request.ReviewAsOwner;
import com.cardee.data_source.remote.api.booking.response.BookingResponse;
import com.cardee.data_source.remote.api.booking.response.entity.BookingEntity;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class RemoteBookingDataSource implements BookingDataSource {

    private static final String TAG = RemoteBookingDataSource.class.getSimpleName();
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
    public void obtainOwnerBookings(String filter, String sort, BookingsCallback bookingsCallback) {
        try {
            Response<BookingResponse> response = api.getOwnerBookings(filter, sort).execute();
            if (response.isSuccessful() && response.body() != null) {
                bookingsCallback.onSuccess(response.body().getBookings());
                return;
            }
            handleErrorResponse(response.body(), bookingsCallback);
        } catch (IOException ex) {
            bookingsCallback.onError(new Error(Error.Type.LOST_CONNECTION, "Internet connection lost"));
        }
    }

    @Override
    public void obtainRenterBookings(String filter, String sort, BookingsCallback bookingsCallback) {

    }

    @Override
    public void obtainBookingById(int id, BookingCallback callback) {

        api.getBookingById(id).subscribe(response -> {
            if (response.isSuccessful()) {
                callback.onSuccess(response.getBooking());
                return;
            }
            handleErrorResponse(response, callback);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
        });
    }

    @Override
    public void sendReviewAsOwner(int bookingId, byte rate, String review, ReviewCallback callback) {
        ReviewAsOwner reviewAsOwner = new ReviewAsOwner();
        reviewAsOwner.setRating((int) rate);
        reviewAsOwner.setReviewFromOwner(review);

        api.sendBookingReview(bookingId, reviewAsOwner).subscribe(noDataResponse -> {
            if (noDataResponse.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(noDataResponse, callback);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
        });
    }

    private void handleErrorResponse(BaseResponse response, BaseCallback bookingsCallback) {
        if (response == null) {
            bookingsCallback.onError(new Error(Error.Type.OTHER, "null"));
            return;
        }
        if (response.getResponseCode() == BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR) {
            bookingsCallback.onError(new Error(Error.Type.SERVER, "Server error"));
        } else if (response.getResponseCode() == BaseResponse.ERROR_CODE_UNAUTHORIZED) {
            bookingsCallback.onError(new Error(Error.Type.AUTHORIZATION, "Unauthorized"));
        } else {
            bookingsCallback.onError(new Error(Error.Type.OTHER, "Undefined error"));
            throw new IllegalArgumentException("Unsupported response code: " + response.getResponseCode());
        }
    }
}

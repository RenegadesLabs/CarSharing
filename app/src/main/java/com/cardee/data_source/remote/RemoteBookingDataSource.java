package com.cardee.data_source.remote;


import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.booking.Bookings;
import com.cardee.data_source.remote.api.booking.request.ReviewAsOwner;
import com.cardee.data_source.remote.api.booking.request.ReviewAsRenter;
import com.cardee.data_source.remote.api.booking.response.BookingResponse;
import com.cardee.domain.bookings.BookingState;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
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
    public void obtainOwnerBookings(String filter, String sort, boolean forceUpdate, BookingsCallback bookingsCallback) {
        try {
            Response<BookingResponse> response = api.getOwnerBookings(filter, sort).execute();
            if (response.isSuccessful() && response.body() != null) {
                bookingsCallback.onSuccess(response.body().getBookings(), true);
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
    public void obtainBookingRentalTerms(int id, RentalTermsCallback callback) {
        api.getBookingRentalTerms(id).subscribe(response -> {
            if (response.isSuccessful()) {
                callback.onSuccess(response.getRentalTerms());
                return;
            }
            handleErrorResponse(response, callback);
        }, throwable -> {
            Log.e(TAG, throwable.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage()));
        });
    }

    @Override
    public void sendReviewAsOwner(int bookingId, byte rate, String review, SimpleCallback callback) {
        ReviewAsOwner reviewAsOwner = new ReviewAsOwner();
        reviewAsOwner.setRating((int) rate);
        reviewAsOwner.setReviewFromOwner(review);

        api.sendReviewAsOwner(bookingId, reviewAsOwner).subscribe(noDataResponse -> {
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

    @Override
    public void sendReviewAsRenter(int bookingId, byte condition, byte comfort, byte owner,
                                   byte overall, String review, SimpleCallback callback) {
        ReviewAsRenter reviewAsRenter = new ReviewAsRenter();
        reviewAsRenter.setCarConditionCleanliness((int) condition);
        reviewAsRenter.setCarComfortPerformance((int) comfort);
        reviewAsRenter.setCarOwner((int) owner);
        reviewAsRenter.setOverallRentalExperience((int) overall);
        reviewAsRenter.setReviewFromRenter(review);

        api.sendReviewAsRenter(bookingId, reviewAsRenter).subscribe(noDataResponse -> {
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

    @Override
    public void changeBookingState(int bookingId, String state, SimpleCallback callback) {
        api.changeBookingState(bookingId, state).subscribe(response -> {
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response, callback);
        }, throwable -> callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage())));
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

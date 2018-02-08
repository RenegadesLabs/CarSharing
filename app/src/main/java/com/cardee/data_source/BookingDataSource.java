package com.cardee.data_source;


import android.net.Uri;

import com.cardee.data_source.remote.api.booking.request.BookingRequest;
import com.cardee.data_source.remote.api.booking.response.entity.BookingCost;
import com.cardee.data_source.remote.api.booking.response.entity.BookingEntity;
import com.cardee.data_source.remote.api.booking.response.entity.BookingRentalTerms;
import com.cardee.data_source.remote.api.booking.response.entity.ChecklistEntity;
import com.cardee.data_source.remote.api.booking.response.entity.CostRequest;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface BookingDataSource {

    void obtainOwnerBookings(String filter, String sort, boolean forceUpdate, BookingsCallback callback);

    void obtainRenterBookings(String filter, String sort, BookingsCallback callback);

    void obtainBookingById(int id, BookingCallback callback);

    void obtainBookingRentalTerms(int id, RentalTermsCallback callback);

    void sendReviewAsOwner(int bookingId, byte rate, String review, SimpleCallback callback);

    void changeBookingState(int bookingId, String state, SimpleCallback callback);

    void sendReviewAsRenter(int bookingId, byte condition, byte comfort, byte owner, byte overall, String review, SimpleCallback callback);

    void getChecklist(int bookingId, ChecklistCallback callback);

    void saveChecklist(int bookingId, String remarks, float tank, int masterMileage, Integer[] imagesIds, SimpleCallback callback);

    void uploadImage(Integer bookingId, Uri uri, ImageCallback callback);

    Disposable getCostBreakdown(CostRequest request, CostCallback callback);

    Disposable requestBooking(BookingRequest request, SimpleCallback callback);

    interface BookingsCallback extends BaseCallback {

        void onSuccess(List<BookingEntity> bookingEntities, boolean updated);
    }

    interface BookingCallback extends BaseCallback {
        void onSuccess(BookingEntity bookingEntity);
    }

    interface RentalTermsCallback extends BaseCallback {
        void onSuccess(BookingRentalTerms rentalTerms);
    }

    interface ChecklistCallback extends BaseCallback {
        void onSuccess(ChecklistEntity checklist);
    }

    interface ImageCallback extends BaseCallback {
        void onSuccess(int imageId);
    }

    interface SimpleCallback extends BaseCallback {
        void onSuccess();
    }

    interface CostCallback extends BaseCallback {
        void onSuccess(BookingCost bookingCost);
    }

    interface BaseCallback {
        void onError(Error error);
    }
}

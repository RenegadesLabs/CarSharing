package com.cardee.data_source.cache;


import android.net.Uri;

import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.remote.api.booking.request.BookingRequest;
import com.cardee.data_source.remote.api.booking.response.entity.CostRequest;

import io.reactivex.disposables.Disposable;

public class LocalBookingDataSource implements BookingDataSource {

    private static LocalBookingDataSource INSTANCE;

    private LocalBookingDataSource() {

    }

    public static LocalBookingDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalBookingDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainOwnerBookings(String filter, String sort, boolean forceUpdate, BookingsCallback bookingsCallback) {

    }

    @Override
    public void obtainRenterBookings(String filter, String sort, BookingsCallback bookingsCallback) {

    }

    @Override
    public void obtainBookingById(int id, BookingCallback callback) {

    }

    @Override
    public void obtainBookingRentalTerms(int id, RentalTermsCallback callback) {

    }

    @Override
    public void sendReviewAsOwner(int bookingId, byte rate, String review, SimpleCallback callback) {

    }

    @Override
    public void changeBookingState(int bookingId, String state, SimpleCallback callback) {

    }

    @Override
    public void sendReviewAsRenter(int bookingId, byte condition, byte comfort, byte owner,
                                   byte overall, String review, SimpleCallback callback) {

    }

    @Override
    public void getChecklist(int bookingId, ChecklistCallback callback) {

    }

    @Override
    public void saveChecklist(int bookingId, String remarks, float tank, int masterMileage, Integer[] imagesIds, SimpleCallback callback) {

    }


    @Override
    public void uploadImage(Integer bookingId, Uri uri, ImageCallback callback) {

    }

    @Override
    public Disposable getCostBreakdown(CostRequest request, CostCallback callback) {
        return null;
    }

    @Override
    public Disposable requestBooking(BookingRequest request, SimpleCallback callback) {
        return null;
    }
}

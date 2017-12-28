package com.cardee.data_source;


import com.cardee.data_source.remote.api.booking.response.entity.BookingEntity;

import java.util.List;

public interface BookingDataSource {

    void obtainOwnerBookings(String filter, String sort, BookingsCallback callback);

    void obtainRenterBookings(String filter, String sort, BookingsCallback callback);

    void obtainBookingById(int id, BookingCallback callback);

    void sendReviewAsOwner(int bookingId, byte rate, String review, ReviewCallback callback);

    void sendReviewAsRenter(int bookingId, byte condition, byte comfort, byte owner, byte overall, String review, ReviewCallback callback);

    interface BookingsCallback extends BaseCallback {

        void onSuccess(List<BookingEntity> bookingEntities);
    }

    interface BookingCallback extends BaseCallback {
        void onSuccess(BookingEntity bookingEntity);
    }

    interface ReviewCallback extends BaseCallback {
        void onSuccess();
    }

    interface BaseCallback {
        void onError(Error error);
    }
}

package com.cardee.data_source;


import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.booking.response.BookingEntity;

import java.util.List;

public interface BookingDataSource {

    void obtainOwnerBookings(String filter, String sort, BookingsCallback callback);

    void obtainRenterBookings(String filter, String sort, BookingsCallback callback);

    void sendReviewAsOwner(int bookingId, byte rate, String review, ReviewCallback callback);

    interface BookingsCallback extends BaseCallback{

        void onSuccess(List<BookingEntity> bookingEntities);
    }

    interface ReviewCallback extends BaseCallback {
        void onSuccess();
    }

    interface BaseCallback{
        void onError(Error error);
    }
}

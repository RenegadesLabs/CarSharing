package com.cardee.data_source;


import com.cardee.data_source.remote.api.booking.response.BookingEntity;

import java.util.List;

public interface BookingDataSource {

    void obtainOwnerBookings(String filter, String sort, Callback callback);

    void obtainRenterBookings(String filter, String sort, Callback callback);

    interface Callback {

        void onSuccess(List<BookingEntity> bookingEntities);

        void onError(Error error);
    }
}

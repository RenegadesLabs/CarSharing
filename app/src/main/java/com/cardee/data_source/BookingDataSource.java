package com.cardee.data_source;


import com.cardee.data_source.remote.api.booking.response.BookingEntity;

import java.util.List;

public interface BookingDataSource {

    void obtainOwnerBookings(Callback callback);

    void obtainRenterBookings(Callback callback);

    interface Callback {

        void onSuccess(List<BookingEntity> bookingEntities);

        void onError(Error error);
    }
}

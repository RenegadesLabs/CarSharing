package com.cardee.data_source;

import com.cardee.data_source.cache.LocalBookingDataSource;
import com.cardee.data_source.remote.RemoteBookingDataSource;
import com.cardee.data_source.remote.api.booking.response.BookingEntity;

import java.util.List;

public class BookingRepository implements BookingDataSource {

    private static BookingRepository INSTANCE;

    private final BookingDataSource localDataSource;
    private final BookingDataSource remoteDataSource;

    private BookingRepository() {
        localDataSource = LocalBookingDataSource.getInstance();
        remoteDataSource = RemoteBookingDataSource.getInstance();
    }

    public static BookingRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookingRepository();
        }
        return INSTANCE;
    }

    @Override
    public void obtainOwnerBookings(Callback callback) {
        remoteDataSource.obtainOwnerBookings(new Callback() {
            @Override
            public void onSuccess(List<BookingEntity> bookingEntities) {
                callback.onSuccess(bookingEntities);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void obtainRenterBookings(Callback callback) {

    }
}

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
    public void obtainOwnerBookings(String filter, String sort, BookingsCallback bookingsCallback) {
        remoteDataSource.obtainOwnerBookings(filter, sort, new BookingsCallback() {
            @Override
            public void onSuccess(List<BookingEntity> bookingEntities) {
                bookingsCallback.onSuccess(bookingEntities);
            }

            @Override
            public void onError(Error error) {
                bookingsCallback.onError(error);
            }
        });
    }

    @Override
    public void obtainRenterBookings(String filter, String sort, BookingsCallback bookingsCallback) {

    }

    @Override
    public void sendReviewAsOwner(int bookingId, byte rate, String review, ReviewCallback callback) {
        remoteDataSource.sendReviewAsOwner(bookingId, rate, review, new ReviewCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

}

package com.cardee.data_source;

import android.util.LruCache;

import com.cardee.data_source.cache.LocalBookingDataSource;
import com.cardee.data_source.remote.RemoteBookingDataSource;
import com.cardee.data_source.remote.api.booking.response.entity.BookingEntity;
import com.cardee.data_source.remote.api.booking.response.entity.BookingRentalTerms;
import com.cardee.domain.bookings.BookingState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BookingRepository implements BookingDataSource {

    private static BookingRepository INSTANCE;

    private final BookingDataSource localDataSource;
    private final BookingDataSource remoteDataSource;

    private LruCache<Integer, BookingEntity> bookingCache;
    //    private LruCache<Integer, BookingRentalTerms> rentalCache;
    private boolean dirtyCache = false;

    private BookingRepository() {
        localDataSource = LocalBookingDataSource.getInstance();
        remoteDataSource = RemoteBookingDataSource.getInstance();
        bookingCache = new LruCache<>(25);
//        rentalCache = new LruCache<>(10);
    }

    public static BookingRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookingRepository();
        }
        return INSTANCE;
    }

    @Override
    public void obtainOwnerBookings(String filter, String sort, BookingsCallback bookingsCallback) {
        if (!dirtyCache) {
            Map<Integer, BookingEntity> snapshot = bookingCache.snapshot();
            if (!snapshot.isEmpty()) {
                Collection<BookingEntity> values = snapshot.values();
                List<BookingEntity> cachedBookings = new ArrayList<>();
                cachedBookings.addAll(values);
                bookingsCallback.onSuccess(cachedBookings);
            }
        }
        remoteDataSource.obtainOwnerBookings(filter, sort, new BookingsCallback() {
            @Override
            public void onSuccess(List<BookingEntity> bookingEntities) {
                int counter = 0;
                for (BookingEntity booking : bookingEntities) {
                    if (++counter >= 25) {
                        break;
                    }
                    bookingCache.put(booking.getBookingId(), booking);
                }
                dirtyCache = false;
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
    public void obtainBookingById(int id, BookingCallback callback) {
        if (!dirtyCache) {
            BookingEntity cachedBooking = bookingCache.get(id);
            if (cachedBooking != null) {
                callback.onSuccess(cachedBooking);
            }
        }
        remoteDataSource.obtainBookingById(id, new BookingCallback() {
            @Override
            public void onSuccess(BookingEntity bookingEntity) {
                callback.onSuccess(bookingEntity);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void obtainBookingRentalTerms(int id, RentalTermsCallback callback) {
        remoteDataSource.obtainBookingRentalTerms(id, new RentalTermsCallback() {
            @Override
            public void onSuccess(BookingRentalTerms rentalTerms) {
                callback.onSuccess(rentalTerms);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }


    @Override
    public void sendReviewAsOwner(int bookingId, byte rate, String review, SimpleCallback callback) {
        remoteDataSource.sendReviewAsOwner(bookingId, rate, review, new SimpleCallback() {
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

    @Override
    public void changeBookingState(int bookingId, String state, SimpleCallback callback) {
        remoteDataSource.changeBookingState(bookingId, state, new SimpleCallback() {
            @Override
            public void onSuccess() {
                BookingEntity cachedBooking = bookingCache.get(bookingId);
                if (cachedBooking != null) {
                    BookingState bookingState = BookingState.fromRequest(state);
                    if (bookingState != null) {
                        cachedBooking.setBookingStateName(bookingState.getValue());
                        cachedBooking.setBookingStateType(bookingState.toString());
                    }
                }
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    void clearCache() {
        bookingCache.evictAll();
        dirtyCache = true;
    }

    @Override
    public void sendReviewAsRenter(int bookingId, byte condition, byte comfort, byte owner, byte overall, String review, SimpleCallback callback) {
        remoteDataSource.sendReviewAsRenter(bookingId, condition, comfort, owner, overall, review, new SimpleCallback() {
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

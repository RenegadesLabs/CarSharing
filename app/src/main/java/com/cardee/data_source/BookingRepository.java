package com.cardee.data_source;

import android.net.Uri;
import android.util.SparseArray;

import com.cardee.data_source.cache.LocalBookingDataSource;
import com.cardee.data_source.remote.RemoteBookingDataSource;
import com.cardee.data_source.remote.api.booking.request.BookingRequest;
import com.cardee.data_source.remote.api.booking.response.entity.BookingEntity;
import com.cardee.data_source.remote.api.booking.response.entity.BookingRentalTerms;
import com.cardee.data_source.remote.api.booking.response.entity.ChecklistEntity;
import com.cardee.data_source.remote.api.booking.response.entity.CostRequest;
import com.cardee.domain.bookings.BookingState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class BookingRepository implements BookingDataSource {

    private static BookingRepository INSTANCE;

    private final BookingDataSource localDataSource;
    private final BookingDataSource remoteDataSource;
    private final Cache cache;

    private BookingRepository() {
        localDataSource = LocalBookingDataSource.getInstance();
        remoteDataSource = RemoteBookingDataSource.getInstance();
        cache = new Cache();
    }

    public static BookingRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookingRepository();
        }
        return INSTANCE;
    }

    @Override
    public void obtainOwnerBookings(String filter, String sort, boolean forceUpdate, BookingsCallback bookingsCallback) {
        if (!forceUpdate && !cache.isDirty()) {
            bookingsCallback.onSuccess(cache.obtainAll(), false);
            return;
        }
        if (!cache.isDirty()) {
            bookingsCallback.onSuccess(cache.obtainAll(), false);
        }
        remoteDataSource.obtainOwnerBookings(filter, sort, forceUpdate, new BookingsCallback() {
            @Override
            public void onSuccess(List<BookingEntity> bookingEntities, boolean updated) {
                cache.updateCache(bookingEntities);
                bookingsCallback.onSuccess(bookingEntities, updated);
            }

            @Override
            public void onError(Error error) {
                bookingsCallback.onError(error);
            }
        });
    }

    @Override
    public void obtainRenterBookings(String filter, String sort, boolean forceUpdate, BookingsCallback bookingsCallback) {
        if (!forceUpdate && !cache.isDirty()) {
            bookingsCallback.onSuccess(cache.obtainAll(), false);
            return;
        }
        if (!cache.isDirty()) {
            bookingsCallback.onSuccess(cache.obtainAll(), false);
        }
        remoteDataSource.obtainRenterBookings(filter, sort, forceUpdate, new BookingsCallback() {
            @Override
            public void onSuccess(List<BookingEntity> bookingEntities, boolean updated) {
                cache.updateCache(bookingEntities);
                bookingsCallback.onSuccess(bookingEntities, updated);
            }

            @Override
            public void onError(Error error) {
                bookingsCallback.onError(error);
            }
        });
    }

    @Override
    public void obtainBookingById(int id, BookingCallback callback) {
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
                cache.invalidate();
                BookingEntity cachedBooking = cache.obtain(bookingId);
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

    @Override
    public void getChecklist(int bookingId, ChecklistCallback callback) {
        remoteDataSource.getChecklist(bookingId, new ChecklistCallback() {
            @Override
            public void onSuccess(ChecklistEntity checklist) {
                callback.onSuccess(checklist);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void saveChecklist(int bookingId, String remarks, float tank, int masterMileage, Integer[] imagesIds, SimpleCallback callback) {
        remoteDataSource.saveChecklist(bookingId, remarks, tank, masterMileage, imagesIds, new SimpleCallback() {
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
    public void extendBooking(int bookingId, String iso8601TimeEnd, SimpleCallback callback) {
        remoteDataSource.extendBooking(bookingId, iso8601TimeEnd, callback);
    }

    @Override
    public void uploadImage(Integer bookingId, Uri uri, ImageCallback callback) {
        remoteDataSource.uploadImage(bookingId, uri, new ImageCallback() {
            @Override
            public void onSuccess(int imageId) {
                callback.onSuccess(imageId);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public Disposable getCostBreakdown(CostRequest request, CostCallback callback) {
        return remoteDataSource.getCostBreakdown(request, callback);
    }

    @Override
    public Disposable requestBooking(BookingRequest request, SimpleCallback callback) {
        return remoteDataSource.requestBooking(request, callback);
    }

    private class Cache {
        private static final int CACHE_SIZE = 25;

        private final SparseArray<BookingEntity> cachedEntries;
        private final List<BookingEntity> cachedBookings;

        boolean dirty = true;

        Cache() {
            cachedEntries = new SparseArray<>();
            cachedBookings = new ArrayList<>();
        }

        void updateCache(List<BookingEntity> bookings) {
            clear();
            int limit = Math.min(CACHE_SIZE, bookings.size());
            for (int i = 0; i < limit; i++) {
                BookingEntity bookingEntity = bookings.get(i);
                cachedBookings.add(bookingEntity);
                cachedEntries.put(bookingEntity.getBookingId(), bookingEntity);
            }
            dirty = false;
        }

        BookingEntity obtain(Integer id) {
            return cachedEntries.get(id);
        }

        List<BookingEntity> obtainAll() {
            return cachedBookings;
        }

        void invalidate() {
            dirty = true;
        }

        void clear() {
            cachedEntries.clear();
            cachedBookings.clear();
        }

        public boolean isDirty() {
            return dirty;
        }
    }
}

package com.cardee.domain.bookings.usecase;


import com.cardee.R;
import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.BookingRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.booking.response.entity.BookingEntity;
import com.cardee.domain.UseCase;
import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.entity.mapper.BookingEntityToBookingMapper;
import com.cardee.domain.util.ListUtil;

import java.util.List;

public class ObtainBookings implements UseCase<ObtainBookings.RequestValues, ObtainBookings.ResponseValues> {

    public enum Strategy {
        OWNER, RENTER
    }

    public enum Sort {
        BOOKING("booking", R.string.booking_sort_date),
        PICKUP("pickup", R.string.booking_sort_pickup_date),
        RETURN("return", R.string.booking_sort_return_date),
        AMOUNT("amount", R.string.booking_sort_amount);

        public final String value;
        private int titleId;

        Sort(String value, int titleId) {
            this.value = value;
            this.titleId = titleId;
        }

        public int getTitleId() {
            return titleId;
        }
    }

    private final BookingRepository repository;
    private final BookingEntityToBookingMapper mapper;

    public ObtainBookings() {
        repository = BookingRepository.getInstance();
        mapper = new BookingEntityToBookingMapper();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        Strategy strategy = values.getStrategy();
        if (strategy == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid strategy: null"));
            return;
        }
        boolean forceUpdate = values.isForceUpdate();
        BookingDataSource.BookingsCallback repositoryCallback = new BookingDataSource.BookingsCallback() {
            @Override
            public void onSuccess(List<BookingEntity> bookingEntities, boolean updated) {
                List<Booking> bookings = ListUtil.map(bookingEntities, entity ->
                        mapper.transform(entity, null));
                callback.onSuccess(new ResponseValues(bookings, updated));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        };
        String filter = values.getFilter() == null ? null : values.getFilter().value;
        String sort = values.getSort() == null ? null : values.getSort().value;
        switch (strategy) {
            case OWNER:
                repository.obtainOwnerBookings(filter, sort, forceUpdate, repositoryCallback);
                break;
            case RENTER:
                repository.obtainRenterBookings(filter, sort, forceUpdate, repositoryCallback);
                break;
        }
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final Strategy strategy;
        private final BookingState filter;
        private final Sort sort;
        private final boolean forceUpdate;

        public RequestValues(Strategy strategy, BookingState filter, Sort sort, boolean forceUpdate) {
            this.strategy = strategy;
            this.filter = filter;
            this.sort = sort;
            this.forceUpdate = forceUpdate;
        }

        public Strategy getStrategy() {
            return strategy;
        }

        public BookingState getFilter() {
            return filter;
        }

        public Sort getSort() {
            return sort;
        }

        public boolean isForceUpdate() {
            return forceUpdate;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final List<Booking> bookings;
        private final boolean updated;

        public ResponseValues(List<Booking> bookings, boolean updated) {
            this.bookings = bookings;
            this.updated = updated;
        }

        public List<Booking> getBookings() {
            return bookings;
        }

        public boolean isUpdated() {
            return updated;
        }
    }
}

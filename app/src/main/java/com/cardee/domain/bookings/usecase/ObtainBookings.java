package com.cardee.domain.bookings.usecase;


import com.cardee.R;
import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.BookingRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.booking.response.BookingEntity;
import com.cardee.domain.UseCase;
import com.cardee.domain.bookings.BookingState;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.entity.mapper.BookingEntityToBookingMapper;
import com.cardee.domain.util.ListUtil;
import com.cardee.domain.util.Mapper;

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
        BookingDataSource.Callback repositoryCallback = new BookingDataSource.Callback() {
            @Override
            public void onSuccess(List<BookingEntity> bookingEntities) {
                List<Booking> bookings = ListUtil.map(bookingEntities, mapper::transform);
                callback.onSuccess(new ResponseValues(bookings));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        };
        String filter = values.getFilter() == null ? null : values.getFilter().name();
        String sort = values.getSort() == null ? null : values.getSort().value;
        switch (strategy) {
            case OWNER:
                repository.obtainOwnerBookings(filter, sort, repositoryCallback);
                break;
            case RENTER:
                repository.obtainRenterBookings(filter, sort, repositoryCallback);
                break;
        }
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final Strategy strategy;
        private final BookingState filter;
        private final Sort sort;

        public RequestValues(Strategy strategy, BookingState filter, Sort sort) {
            this.strategy = strategy;
            this.filter = filter;
            this.sort = sort;
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
    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final List<Booking> bookings;

        public ResponseValues(List<Booking> bookings) {
            this.bookings = bookings;
        }

        public List<Booking> getBookings() {
            return bookings;
        }
    }
}

package com.cardee.domain.bookings.usecase;


import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.BookingRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.booking.response.BookingEntity;
import com.cardee.domain.UseCase;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.entity.mapper.BookingEntityToBookingMapper;
import com.cardee.domain.util.ListUtil;
import com.cardee.domain.util.Mapper;

import java.util.List;

public class ObtainBookings implements UseCase<ObtainBookings.RequestValues, ObtainBookings.ResponseValues> {

    public enum Strategy {
        OWNER, RENTER
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
        switch (strategy) {
            case OWNER:
                repository.obtainOwnerBookings(repositoryCallback);
                break;
            case RENTER:
                repository.obtainRenterBookings(repositoryCallback);
                break;
        }
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final Strategy strategy;

        public RequestValues(Strategy strategy) {
            this.strategy = strategy;
        }

        public Strategy getStrategy() {
            return strategy;
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

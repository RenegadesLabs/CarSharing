package com.cardee.domain.bookings.usecase;


import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.BookingRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.booking.response.entity.BookingEntity;
import com.cardee.domain.UseCase;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.entity.mapper.BookingEntityToBookingMapper;

public class GetBooking implements UseCase<GetBooking.RequestValues, GetBooking.ResponseValues> {

    private final BookingRepository repository;
    private final BookingEntityToBookingMapper mapper;

    public GetBooking() {
        repository = BookingRepository.getInstance();
        mapper = new BookingEntityToBookingMapper();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        repository.obtainBookingById(values.getBookingId(), new BookingDataSource.BookingCallback() {
            @Override
            public void onSuccess(BookingEntity bookingEntity) {
                Booking booking = mapper.transform(bookingEntity);
                callback.onSuccess(new ResponseValues(booking));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        int bookingId;

        public RequestValues(int bookingId) {
            this.bookingId = bookingId;
        }

        public int getBookingId() {
            return bookingId;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final Booking booking;

        public ResponseValues(Booking booking) {
            this.booking = booking;
        }

        public Booking getBooking() {
            return booking;
        }
    }
}

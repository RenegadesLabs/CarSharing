package com.cardee.domain.bookings.usecase;

import com.cardee.data_source.BookingRepository;
import com.cardee.domain.UseCase;

public class GetBookingEarnings implements UseCase<GetBookingEarnings.RequestValues, GetBookingEarnings.ResponseValues> {

    private final BookingRepository repository;

    public GetBookingEarnings() {
        repository = BookingRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {

    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int carId;
        private final String timeBegin;
        private final String timeEnd;
        private final boolean isCurbside;
        private final float latitude;
        private final float longitude;
        private final boolean isBookingByDay;

        public RequestValues(int carId, String timeBegin, String timeEnd, boolean isCurbside,
                             float latitude, float longitude, boolean isBookingByDay) {
            this.carId = carId;
            this.timeBegin = timeBegin;
            this.timeEnd = timeEnd;
            this.isCurbside = isCurbside;
            this.latitude = latitude;
            this.longitude = longitude;
            this.isBookingByDay = isBookingByDay;
        }

        public int getCarId() {
            return carId;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

    }
}

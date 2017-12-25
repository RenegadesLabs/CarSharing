package com.cardee.data_source.cache;


import com.cardee.data_source.BookingDataSource;

public class LocalBookingDataSource implements BookingDataSource {

    private static LocalBookingDataSource INSTANCE;

    private LocalBookingDataSource() {

    }

    public static LocalBookingDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalBookingDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainOwnerBookings(String filter, String sort, BookingsCallback bookingsCallback) {

    }

    @Override
    public void obtainRenterBookings(String filter, String sort, BookingsCallback bookingsCallback) {

    }

    @Override
    public void sendReviewAsOwner(int bookingId, byte rate, String review, ReviewCallback callback) {

    }
}

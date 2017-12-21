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
    public void obtainOwnerBookings(Callback callback) {

    }

    @Override
    public void obtainRenterBookings(Callback callback) {

    }
}
